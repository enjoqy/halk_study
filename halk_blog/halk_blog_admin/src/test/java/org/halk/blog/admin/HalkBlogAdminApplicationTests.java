package org.halk.blog.admin;

import org.halk.blog.admin.controller.BlogController;
import org.halk.blog.admin.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class HalkBlogAdminApplicationTests {

	@Autowired
	private BlogController blogController;

	@Test
	void contextLoads() {

//		Blog blog = Blog.builder()
//				.author("halk")
//				.content("123456789")
//				.title("hello blog")
//				.build();
//		ResponseEntity<Void> voidResponseEntity = blogController.addBlog(blog);
//
//		System.out.println(voidResponseEntity.getStatusCode());
	}

}
