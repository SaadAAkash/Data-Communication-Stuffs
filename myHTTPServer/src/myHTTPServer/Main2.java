package myHTTPServer;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Main2{
    
    //////////root//////////////////
	
	static class Root implements HttpHandler{
    	// contains all the instructions and reference links attached 
    	//to the file name text fields respectively.
        @Override
        public void handle(HttpExchange he) throws IOException {
        	
            String str = "<h1><font color=" + "blue" + ">To download the file(s), just click on the file link. </font></h1>"; 
            str += "<h2><font color=" + "green" + ">Thank You.</font></h2>";
            str+= "<h2> <font color=" + "red" + "> Happy downloading!</font> </h2>";
            str+= "<a href=\"http://192.168.0.103:8080/file1/\" >New Image1.png</a><br>";
            str+="<a href=\"http://192.168.0.103:8080/file2/\">New Image2.jpg</a><br>";
            str+="<a href=\"http://192.168.0.103:8080/file3/\">TestFile1.pdf</a><br>";
            str+="<a href=\"http://192.168.0.103:8080/file3/\">TestFile2.pdf</a><br>";
            str+="<a href=\"http://192.168.0.103:8080/file3/\">TestFile3.pdf</a><br>";

            he.sendResponseHeaders(200, str.length());
            OutputStream os = he.getResponseBody();
            os.write(str.getBytes());
            os.close();
        }
        
    }
    //////////////////root ends//////////////////////////
    
    
	static class MyHeader implements HttpHandler
	//
	{

        @Override
        public void handle(HttpExchange he) throws IOException {
            Headers headers = he.getRequestHeaders();
            Set<Map.Entry<String, List<String> > > entries = headers.entrySet();
            String str = "";
            for (Map.Entry<String, List<String>> it : entries) {
                str += it.toString() + "\n";
            }
            he.sendResponseHeaders(200, str.length());
            OutputStream os = he.getResponseBody();
            os.write(str.toString().getBytes());
            os.close();
        }
        
    }
    
    ///////////////myheader ends//////////////////////
    
    static public void splitQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }
    
    
    
    
    
    static class MyGetHeader implements HttpHandler{

        @Override
        public void handle(HttpExchange he) throws IOException {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
            Map<String, Object> parameters = new HashMap<String, Object>();
            URI requestedUri = he.getRequestURI();
            String query = requestedUri.getRawQuery();
            splitQuery(query, parameters);

            String str = "";
            for (String key : parameters.keySet()) {
                str += key + " = " + parameters.get(key) + "\n";
            }
            he.sendResponseHeaders(200, str.length());
            OutputStream os = he.getResponseBody();
            os.write(str.toString().getBytes());

            os.close();
        }
        
    }
    
    static class MyPostHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange he) throws IOException {
            
            Map<String, Object> parameters = new HashMap<String, Object>();
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            splitQuery(query, parameters);

            String str = "";
            for (String key : parameters.keySet()) {
                str+= key + " = " + parameters.get(key) + "\n";
            }
            he.sendResponseHeaders(200, str.length());
            OutputStream os = he.getResponseBody();
            os.write(str.toString().getBytes());
            os.close();
        }
    }
    
    ///////////////////////////////////////////////////////
    
    static class GetHandler implements HttpHandler
    //file number nie file er link set kre display kre output stream e
    {
        
        int number;

        public GetHandler(int no) {
            number = no;
        }
        
        

        @Override
        public void handle(HttpExchange t) throws IOException {

            Headers h = t.getResponseHeaders();

            File file = null ;
             
            switch(this.number){
                case 0:
                    file = new File("C:\\Users\\Akash\\Desktop\\a.jpg");
                    break;
                case 1:
                    file = new File("C:\\Users\\Akash\\Desktop\\a.jpg");
                    break;
                case 2:
                    //file = new File("/home/anando/Pictures/HTTPServer.java");
                	file = new File("C:\\Users\\Akash\\Desktop\\a.jpg");
                	
                    break;
            }
            
            byte[] bytearray = new byte[(int) file.length()];
            FileInputStream fin = new FileInputStream(file);
            BufferedInputStream br = new BufferedInputStream(fin);
            br.read(bytearray, 0, bytearray.length);

            t.sendResponseHeaders(200, file.length());
            OutputStream os = t.getResponseBody();
            os.write(bytearray, 0, bytearray.length);
            os.close();
        }
    }
    ////////////////////////////////////////////////
 
    public static void main(String [] args) throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/", new Root());
        
        server.createContext("/file1",new GetHandler(0));
        server.createContext("/file2",new GetHandler(1));
        server.createContext("/file3",new GetHandler(2));
        
        server.createContext("/header",new MyHeader());
        server.createContext("/get",new MyGetHeader());
        server.createContext("/post",new MyPostHandler());
        
        server.setExecutor(null);
        server.start();
    }
}