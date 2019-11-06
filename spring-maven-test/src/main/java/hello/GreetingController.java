package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.io.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static String file_path = "/afs/cs.wisc.edu/u/k/m/kmulligan/private/RFixer/tests/benchmark_explicit";
    private static String jar_path = "/afs/cs.wisc.edu/u/k/m/kmulligan/private/RFixer/target/regfixer.jar";

    private static String cmd = "java -jar " + jar_path + " -m 1 -max -base -c -t fix --file " + file_path;

    @RequestMapping(value="/greeting", method=RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Runtime r = Runtime.getRuntime();
        int retcode = -1;
        try
        {
            Process p = r.exec(cmd);
            new Thread(new Runnable() {
                public void run() {
                 BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                 String line = null; 
            
                 try {
                    while ((line = input.readLine()) != null)
                        System.out.println(line);
                 } catch (IOException e) {
                        e.printStackTrace();
                 }
                }
            }).start();
            retcode = p.waitFor();
        }
        catch (Exception e)
        {
            return new Greeting(retcode, e.toString());
        }
        //error = p.getErrorStream();O
        return new Greeting(retcode,
                            String.format(template, name));
        
    }
}
