public class Application {
     public static void main(String[] args) {
         // application - param 1 = command, param 2 = cust/order id, new desc
         DatabaseChecker dbChecker = new DatabaseChecker("config.properties");
         if (args.length == 3){
            dbChecker.run(args[0],(Integer.valueOf(args[1])), args[2]);
         } else if (args.length == 2) {
            dbChecker.run(args[0], Integer.valueOf(args[1]), "default description");
         } else {
            System.out.println("Invalid command. Please input command, and related id.");
         }
     }
}