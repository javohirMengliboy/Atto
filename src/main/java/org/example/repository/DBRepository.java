package org.example.repository;

import org.springframework.stereotype.Repository;
import java.sql.*;
@Repository
public class DBRepository {
    public void createProfileTable(){
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "create table if not exists profile" +
                    "(" +
                    "id serial primary key," +
                    "name varchar(30) not null," +
                    "surname varchar(30) not null," +
                    "phone varchar(13) unique," +
                    "login varchar(20) unique," +
                    "password varchar(16)," +
                    "created_date timestamp default now()," +
                    "status varchar(15)," +
                    "role varchar(20)" +
                    ");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCardTable(){
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "create table if not exists card" +
                    "(" +
                    "id serial primary key," +
                    "number bigint not null unique," +
                    "exp_date varchar(5) not null,"+
                    "balance numeric," +
                    "status varchar(15)," +
                    "phone varchar(13) unique," +
                    "created_date timestamp default now()," +
                    "profile_id int" +
                    ");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTerminalTable(){
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "create table if not exists terminal" +
                    "(" +
                    "id serial primary key," +
                    "code varchar not null unique," +
                    "address varchar not null," +
                    "status varchar(15)," +
                    "created_date timestamp default now()" +
                    ");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTransactionTable(){
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "create table if not exists transactions" +
                    "(" +
                    "id serial primary key," +
                    "card_number bigint not null," +
                    "amount numeric," +
                    "terminal_code varchar(15)," +
                    "type varchar(15)," +
                    "created_date timestamp default now()," +
                    "foreign key (card_number) references card(number)," +
                    "foreign key (terminal_code) references terminal(code)" +
                    ");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMakeTransactionProducer(){
        try {
            Connection connection = DBConnection.getConnection();
            String sql = """
                    create or replace procedure make_transaction(card_num bigint, terminal_code varchar, amount numeric, atto_card_number bigint)
                    language plpgsql
                    as $$
                    declare
                    temp_num card%rowtype;
                    temp_code terminal%rowtype;
                    t_balance card%rowtype;
                    begin
                    	select * from card into temp_num where number = card_num;
                    	if not found temp_num then
                    		RAISE EXCEPTION 'Card not found 1';
                    	end if;
                    	
                    	select * from card into t_balance where number = card_num\s
                    		and balance >= amount;
                    	if not found t_balance then
                    		RAISE EXCEPTION 'Balance not found 1';
                    	end if;
                    	
                    	select * from terminal into temp_code where code = terminal_code;
                    	if not found temp_code then
                    		RAISE EXCEPTION 'Terminal not found 1';
                    	end if;
                    	
                    	update card set balance = balance-amount where number = card_num;
                    	update card set balance = balance+amount where number = atto_card_number;
                    	
                    	insert into transactions(card_number, amount, terminal_code, type)
                    		values(card_num, amount, terminal_code, 'Payment');
                    		
                    commit;
                    end; $$""";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
