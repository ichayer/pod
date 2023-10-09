package ar.edu.itba.pod.graphql.blog;

import ar.edu.itba.pod.graphql.blog.dao.AuthorDao;
import ar.edu.itba.pod.graphql.blog.dao.PostDao;
import ar.edu.itba.pod.graphql.blog.model.Author;
import ar.edu.itba.pod.graphql.blog.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// @Controller
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final AuthorDao authorDao;

    private final PostDao postDao;

    public PostController(final PostDao postDao, final AuthorDao authorDao) {
        this.postDao = postDao;
        this.authorDao = authorDao;
    }

    @QueryMapping
    public List<Post> recentPosts(@Argument int count, @Argument int offset) {
        logger.info("Getting recent posts");
        return postDao.getRecentPosts(count, offset);
    }

    //    Cuando alguien te pida el campo author de la clase Post,
    //    llama a este metodo porque Post no tiene el campo author!
    //    @issue: n+1 query issue, we have to batch it!
    @SchemaMapping(typeName = "Post", field = "author")
    public Author getAuthor(Post post) {
        logger.info("Getting Author from Post");
        return authorDao.getAuthor(post.authorId());
    }
}
