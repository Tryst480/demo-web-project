package edu.csupomona.cs480.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.csupomona.cs480.App;
import edu.csupomona.cs480.data.GpsProduct;
import edu.csupomona.cs480.data.StringCompareRequest;
import edu.csupomona.cs480.data.User;
import edu.csupomona.cs480.data.provider.GpsProductManager;
import edu.csupomona.cs480.data.provider.UserManager;
import info.debatty.java.stringsimilarity.Levenshtein;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Splitter;

import org.apache.commons.math3.random.RandomDataGenerator;

/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

    /**
     * When the class instance is annotated with
     * {@link Autowired}, it will be looking for the actual
     * instance from the defined beans.
     * <p>
     * In our project, all the beans are defined in
     * the {@link App} class.
     */
    @Autowired
    private UserManager userManager;
    @Autowired
    private GpsProductManager gpsProductManager;

    /**
     * This is a simple example of how the HTTP API works.
     * It returns a String "OK" in the HTTP response.
     * To try it, run the web application locally,
     * in your web browser, type the link:
     *     http://localhost:8080/cs480/ping
     */
    
    
    
    @RequestMapping(value = "/cs580/ping", method = RequestMethod.GET)
    String healthCheck() {
        // You can replace this with other string,
        // and run the application locally to check your changes
        // with the URL: http://localhost:8080/
        return "OK-CS480-Demo";
    }
    
    /**
     * This is a simple example of how to use a data manager
     * to retrieve the data and return it as an HTTP response.
     * <p>
     * Note, when it returns from the Spring, it will be
     * automatically converted to JSON format.
     * <p>
     * Try it in your web browser:
     *     http://localhost:8080/cs480/user/user101
     */
    @RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.GET)
    User getUser(@PathVariable("userId") String userId) {
        User user = userManager.getUser(userId);
        return user;
    }

    /**
     * This is an example of sending an HTTP POST request to
     * update a user's information (or create the user if not
     * exists before).
     *
     * You can test this with a HTTP client by sending
     *  http://localhost:8080/cs480/user/user101
     *      name=John major=CS
     *
     * Note, the URL will not work directly in browser, because
     * it is not a GET request. You need to use a tool such as
     * curl.
     *
     * @param id
     * @param name
     * @param major
     * @return
     */
    @RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.POST)
    User updateUser(
            @PathVariable("userId") String id,
            @RequestParam("name") String name,
            @RequestParam(value = "major", required = false) String major) {
        User user = new User();
        user.setId(id);
        user.setMajor(major);
        user.setName(name);
        userManager.updateUser(user);
        return user;
    }

    @RequestMapping(value = "/alex", method = RequestMethod.GET)
    String alex() {
        // You can replace this with other string,
        // and run the application locally to check your changes
        // with the URL: http://localhost:8080/
        return "My name is Alex, have a nice day.";
    }
    
    @RequestMapping(value = "/compstr", method = RequestMethod.POST)
    String compareStr(@RequestBody StringCompareRequest input) {
        Levenshtein l = new Levenshtein();
        return Double.toString(l.distance(input.str1, input.str2));
    }

    @RequestMapping(value = "/ned", method = RequestMethod.GET)
    String ned() {
        // You can replace this with other string,
        // and run the application locally to check your changes
        // with the URL: http://localhost:8080/
        return "Hey, it's ya boi, Ned. Make sure you like and subscribe down below.";
    }

    @RequestMapping(value = "ned/random", method = RequestMethod.GET)
    String random() {
        RandomDataGenerator randomNumGenerator = new RandomDataGenerator();
        int luckyNum = randomNumGenerator.nextInt(0, 100);
        return String.format("Your Lucky Number is %d.", luckyNum);
    }

    @RequestMapping(value = "/justin", method = RequestMethod.GET)
    String justin() {
        // You can replace this with other string,
        // and run the application locally to check your changes
        // with the URL: http://localhost:8080/
        return "Did you know there are more cells in your brain than there are brains in your body?";
    }
    
    @RequestMapping(value = "/Peter", method = RequestMethod.GET)
    String peter() {
        // You can replace this with other string,
        // and run the application locally to check your changes
        // with the URL: http://localhost:8080/
        return "The new logo is a waste of money, should have used it for pretty much anything else...";
    }
    
    @RequestMapping(value = "/Tryst/Ted", method = RequestMethod.GET)
    String dog() {
        // You can replace this with other string,
        // and run the application locally to check your changes
        // with the URL: http://localhost:8080/
        return "working";
    }
    
    /**
     * This API deletes the user. It uses HTTP DELETE method.
     *
     * @param userId
     */
    @RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.DELETE)
    void deleteUser(
            @PathVariable("userId") String userId) {
        userManager.deleteUser(userId);
    }

    /**
     * This API lists all the users in the current database.
     *
     * @return
     */
    @RequestMapping(value = "/cs480/users/list", method = RequestMethod.GET)
    List<User> listAllUsers() {
        return userManager.listAllUsers();
    }
    
    @RequestMapping(value = "/cs480/gps/list", method = RequestMethod.GET)
    List<GpsProduct> listGpsProducts() {
        return gpsProductManager.listAllGpsProducts();
    }

    /*********** Web UI Test Utility **********/
    /**
     * This method provide a simple web UI for you to test the different
     * functionalities used in this web service.
     */
    @RequestMapping(value = "/cs480/home", method = RequestMethod.GET)
    ModelAndView getUserHomepage() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("users", listAllUsers());
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/cs480/html", method = RequestMethod.GET)
    public void getFormParams(String html){
          
        Document doc = Jsoup.parse(html);
     
        //HTML form id
        Element loginform = doc.getElementById("your_form_id");

        Elements inputElements = loginform.getElementsByTag("input");

        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");
        }
     
    }

    @RequestMapping(value = "peter/a4", method = RequestMethod.GET)
    public void testSplitter(String html){
        System.out.println(Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split(html));
    }
}
