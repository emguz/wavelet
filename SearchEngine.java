import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    
    ArrayList<String> strList = new ArrayList<String>();
    String listOfStrings = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "Strings: " + listOfStrings;
        } else if (url.getPath().equals("/search")) {
            String[] param = url.getQuery().split("=");
            int numMatches = 0;
            for(int i = 0; i < strList.size(); i++) {
                if(strList.get(i).contains(param[1])) {
                    listOfStrings += strList.get(i) + " ";
                    numMatches ++;
                } 
            }
            if(numMatches == 0) {
                return String.format("No matches found.");
            }
            return String.format(listOfStrings);
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                strList.add(parameters[1]);
                return String.format(parameters[1] + " added to library.");
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 and 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
