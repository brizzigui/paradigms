package gym_system;

import java.sql.*;
import java.util.Scanner;

// to run, open the gym_system folder in intellij and input mysql port, user, and password
// requires jdk and mysql to run

public class Main
{
    public static Connection con;
    public static Scanner sc = new Scanner(System.in);

    private static void create_tables(){
        try {
            var statement = con.createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS gym;");
            statement.execute("USE gym;");
            statement.execute("CREATE TABLE IF NOT EXISTS members " +
                    "(cpf VARCHAR(255), " +
                    "name VARCHAR(255), " +
                    "birth_year INT, " +
                    "birth_month INT, " +
                    "birth_day INT, " +
                    "plan_code VARCHAR(255)," +
                    "credit_card_num VARCHAR(255)," +
                    "credit_card_cvv VARCHAR(255)," +
                    "credit_card_expiry_year INT," +
                    "credit_card_expiry_month INT," +
                    "subscription_start_day INT," +
                    "subscription_start_month INT," +
                    "subscription_start_year INT);");

            statement.execute("CREATE TABLE IF NOT EXISTS exercises " +
                    "(code VARCHAR(255)," +
                    "name VARCHAR(255)," +
                    "activated_muscles VARCHAR(255));");

            statement.execute("CREATE TABLE IF NOT EXISTS plans " +
                    "(code VARCHAR(255)," +
                    "name VARCHAR(255)," +
                    "monthly_cost DOUBLE);");


            // uses cpf and code to track multiple unique splits that a single user may hold
            // splits are unique to user
            // each row holds a single exercise and a split is accessed by reading multiple
            // rows with same cpf and code

            statement.execute("CREATE TABLE IF NOT EXISTS splits" +
                    "(cpf VARCHAR(255)," +
                    "split_code VARCHAR(255)," +
                    "exercise_code VARCHAR(255)," +
                    "min_reps INT," +
                    "max_reps INT," +
                    "exercise_load DOUBLE," +
                    "rest_time DOUBLE);");


            // attendances table is kinda weird, but hear me out
            // records every exercise done by person during a certain day
            // why? if person did something, they were there AND we track their weight
            // progression using a single table. sounds complicated. but isn't.

            statement.execute("CREATE TABLE IF NOT EXISTS attendances " +
                    "(cpf VARCHAR(255)," +
                    "day INT," +
                    "month INT," +
                    "year INT," +
                    "exercise_code VARCHAR(255)," +
                    "exercise_load DOUBLE);");
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/",
                    "root",
                    "2004"
            );
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        create_tables();

        while(true) {
            int choice = Options.menu();
            switch(choice){
                case 1:
                    choice = Options.member_menu();
                    switch(choice){
                        case 1:
                            Member.add_member();
                            break;

                        case 2:
                            Member.remove_member();
                            break;

                        case 3:
                            Member.change_member();
                            break;

                        case 4:
                            Member.list_all();
                            break;

                        case 5:
                            Member.search_by_cpf();
                            break;

                        case 6:
                            Member.search_by_name();
                            break;

                        case 7:
                            Member.split_menu();
                            break;

                        case 8:
                            Member.register_attendance();
                            break;

                        case 9:
                            Member.set_subscription();
                            break;

                        case 10:
                            break;
                    }
                    break;

                case 2:
                    choice = Options.plan_menu();
                    switch(choice){
                        case 1:
                            Plan.add_plan();
                            break;

                        case 2:
                            Plan.remove_plan();
                            break;

                        case 3:
                            Plan.change_plan();
                            break;

                        case 4:
                            Plan.list_all();
                            break;

                    }
                    break;

                case 3:
                    choice = Options.exercise_menu();
                    switch(choice){
                        case 1:
                            Exercise.add_exercise();
                            break;

                        case 2:
                            Exercise.remove_exercise();
                            break;

                        case 3:
                            Exercise.edit_exercise();
                            break;

                        case 4:
                            Exercise.print_all();
                            break;
                    }
                    break;

                case 4:
                    choice = Options.report_menu();
                    switch(choice){
                        case 1:
                            Member.see_member_attendance();
                            break;

                        case 2:
                            Member.see_exercise_evolution();
                            break;

                        case 3:
                            break;
                    }
                    break;

                case 9:
                    return;

            }
        }
    }
}