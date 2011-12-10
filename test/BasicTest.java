import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;
import java.util.List;

public class BasicTest extends UnitTest {
	
	@Before
	public void setup(){
		Fixtures.deleteDatabase();
	}

    @Test
    public void createAndRetrieveUser(){
		new Users("test@tester.com", "testing_password", "testing_fullname").save();
		Users test = Users.find("byEmail", "test@tester.com").first();
		assertNotNull(test);
		assertEquals("testing_fullname", test.fullName); 
	}
	
	@Test
	public void emailAndPasswordTest(){
		new Users("test@tester.com", "testing_password", "testing_fullname").save();
		Users test = Users.connect("test@tester.com", "testing_password");
		
		//Tests
		assertNotNull(test);
		assertEquals("testing_fullname", test.fullName);
		assertNull(Users.connect("test@tester.com", "bad_password"));
	}
	
	@Test
	public void createPost() {
		// Create a new user and save it
		Users bob = new Users("bob@gmail.com", "secret", "Bob").save();
    
		// Create a new post
		new Posts(bob, "My first post", "Hello world").save();
    
		// Test that the post has been created
		assertEquals(1, Posts.count());
    
		// Retrieve all posts created by Bob
		List<Posts> bobPosts = Posts.find("byAuthor", bob).fetch();
    
		// Tests
		assertEquals(1, bobPosts.size());
		Posts firstPost = bobPosts.get(0);
		assertNotNull(firstPost);
		assertEquals(bob, firstPost.author);
		assertEquals("My first post", firstPost.title);
		assertEquals("Hello world", firstPost.content);
		assertNotNull(firstPost.postedAt);
	}
	
	@Test
	public void useTheCommentsRelation() {
		// Create a new user and save it
		Users bob = new Users("bob@gmail.com", "secret", "Bob").save();
 
		// Create a new post
		Posts bobPost = new Posts(bob, "My first post", "Hello world").save();
 
		// Post a first comment
		bobPost.addComment("Jeff", "Nice post");
		bobPost.addComment("Tom", "I knew that !");
 
		// Count things
		assertEquals(1, Users.count());
		assertEquals(1, Posts.count());
		assertEquals(2, Comments.count());
 
		// Retrieve Bob's post
		bobPost = Posts.find("byAuthor", bob).first();
		assertNotNull(bobPost);
 
		// Navigate to comments
		assertEquals(2, bobPost.comments.size());
		assertEquals("Jeff", bobPost.comments.get(0).author);
    
		// Delete the post
		bobPost.delete();
    
		// Check that all comments have been deleted
		assertEquals(1, Users.count());
		assertEquals(0, Posts.count());
		assertEquals(0, Comments.count());
	}
	
	@Test
	public void fullTest() {
	    Fixtures.loadModels("data.yml");
	 
	    // Count things
	    assertEquals(2, Users.count());
	    assertEquals(3, Posts.count());
	    assertEquals(3, Comments.count());
	 
	    // Try to connect as users
	    assertNotNull(Users.connect("bob@gmail.com", "secret"));
	    assertNotNull(Users.connect("jeff@gmail.com", "secret"));
	    assertNull(Users.connect("jeff@gmail.com", "badpassword"));
	    assertNull(Users.connect("tom@gmail.com", "secret"));
	 
	    // Find all of Bob's posts
	    List<Posts> bobPosts = Posts.find("author.email", "bob@gmail.com").fetch();
	    assertEquals(2, bobPosts.size());
	 
	    // Find all comments related to Bob's posts
	    List<Comments> bobComments = Comments.find("post.author.email", "bob@gmail.com").fetch();
	    assertEquals(3, bobComments.size());
	 
	    // Find the most recent post
	    Posts frontPost = Posts.find("order by postedAt desc").first();
	    assertNotNull(frontPost);
	    assertEquals("About the model layer", frontPost.title);
	 
	    // Check that this post has two comments
	    assertEquals(2, frontPost.comments.size());
	 
	    // Post a new comment
	    frontPost.addComment("Jim", "Hello guys");
	    assertEquals(3, frontPost.comments.size());
	    assertEquals(4, Comments.count());
	}

}
