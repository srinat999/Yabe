package controllers;

import play.*;
import play.mvc.*;
import play.Play;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
		Posts frontPost = Posts.find("order by postedAt desc").first();
		List<Posts> olderPosts = Posts.find("order by postedAt desc").from(1).fetch(10);
        render(frontPost, olderPosts);
    }
    
    @Before
	static void addDefaults() {
		renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
		renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
	}
}
