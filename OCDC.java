
import java.util.*;
import java.util.List;
//Author Haikal.Hafiz
public class OCDC {

    public static Map<String, List<String>> client = new HashMap<>();
    public static String input;
    public static String userName;
    public static Integer topup;
    public static Integer payment;
    public static String payToUser;


    public static void main (String[] arg){
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("*****************************************");
            System.out.println("Enter command ");
         input = scan.next();
         switch (input) {
             case "login": {
                 userName = scan.next();
                 System.out.println("===== Welcome to login section ======");
                 getClient(userName);
                 break;
             }
             case "topup": {
                 topup = scan.nextInt();
                 System.out.println("===== Welcome to topup section ======");
                 topup(userName,topup);
                 break;
             }
             case "pay" : {
                 payToUser =scan.next();
                 payment = scan.nextInt();
                 System.out.println("===== Welcome to pay section ======");
                 setPayment(userName,payToUser,payment);
                 break;
             }

            default: System.out.println("Please enter valid input");
         }
        }
        while (!input.equals("end"));
    }
    public static void getClient(String userName){
        if(client.containsKey(userName)){
            System.out.println("User has register");
            for(Map.Entry<String,List<String>> entry : client.entrySet()){
                if(entry.getKey().contains(userName)){
                    System.out.println("Hello, "+ entry.getKey());
                    System.out.println("Your Balance is  "+ entry.getValue().get(0));
                    System.out.println("Your Loan is  "+ entry.getValue().get(1));
                }
            }
        }
        else{
            System.out.println("User not register");
            //Initialize balance and loan
            //[balance,loan,whoPay] index
            List<String> accountDetails = new ArrayList<>();
            accountDetails.add(String.valueOf(0));
            accountDetails.add(String.valueOf(0));
            client.put(userName,accountDetails);
            for(Map.Entry<String,List<String>> entry : client.entrySet()){
                if(entry.getKey().contains(userName)){
                    System.out.println("Hello from not register, "+ entry.getKey());
                    System.out.println("Your Balance is  "+ entry.getValue().get(0));
                }
            }
        }
    }

    public static void topup(String userName,Integer topup){
        System.out.println("Username " + userName);
        System.out.println("topup value  " + topup);
        if(client.containsKey(userName)){
            Integer loan = Integer.parseInt(client.get(userName).get(1));
            if(loan > 0 ){
                Integer finalLoan = loan - topup;
                System.out.println("Final loan " + finalLoan);
                if(finalLoan < 0){
                    client.get(userName).set(1,String.valueOf(0));
                }
                client.get(userName).set(1,String.valueOf(finalLoan));
                client.get(userName).set(0,String.valueOf(0));
            }else{
                System.out.println("Loan is 0");
                int total = 0;
                for(Map.Entry<String,List<String>> entry : client.entrySet()){
                    if(entry.getKey().contains(userName)){
                        System.out.println("user " + entry.getKey());
                        int amount = Integer.parseInt(entry.getValue().get(0));
                        total = amount + topup;
                        client.get(userName).set(0,String.valueOf(total));
                    }
                }
            }
        }else{
            System.out.println("User not register yet");
        }

    }

    public static void setPayment(String userName,String payToUser,Integer payment){
        Integer totalFinal = 0;
        Integer loan = 0;
        if(client.containsKey(userName) && client.containsKey(payToUser)){
            Integer userNameBalance = Integer.parseInt(client.get(userName).get(0));
            Integer payToUserBalance = Integer.parseInt(client.get(payToUser).get(0));
            Integer userNameFinalBalance = userNameBalance - payment;
            Integer payToUserFinalBalance = payToUserBalance + payment;
            if(userNameFinalBalance < 0 ){
                //TODO loan
                loan = Math.abs(userNameFinalBalance);
                System.out.println("print loan " + loan );
                client.get(userName).set(1,String.valueOf(loan));
                payToUserBalance = userNameBalance + payToUserBalance;
                client.get(payToUser).set(0,String.valueOf(payToUserBalance));
                client.get(userName).set(0,String.valueOf(0));
            }else {
                client.get(userName).set(0,String.valueOf(userNameFinalBalance));
                client.get(payToUser).set(0,String.valueOf(payToUserFinalBalance));
                System.out.println("your balance is " + userNameFinalBalance);
            }

            System.out.println("Transferred to " + payToUser);
            System.out.println("Own to " + payToUser + " " + loan);
        }else{
            System.out.println("First client or second client is not register yet");
        }
    }
}
