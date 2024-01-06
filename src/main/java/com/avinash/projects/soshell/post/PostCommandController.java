package com.avinash.projects.soshell.post;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ShellComponent
public class PostCommandController {
    private PostService postService;

    public PostCommandController(PostService postService) {
        this.postService = postService;
    }

    @ShellMethod(key = "post", value = "post related commands")
    public String user() {
        return "User related commands. Usage only with sub-commands - create,rate,,delete,view,list";
    }

    @ShellMethod(key = "post create", value = "create flow for a new post")
    public String createPost(
            @ShellOption(value = { "--username", "-u" }, defaultValue = ShellOption.NULL) String username,
            @ShellOption(value = { "--content", "-c" }, defaultValue = ShellOption.NULL) String content) {
        log.info("new create post request for username {}", username);
        Post post;
        try {
            post = postService.createPost(username, content);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return "bad request - content should not be null";
        } catch (Exception e) {
            log.info(e.toString());
            return "internal server error";
        }
        return post.toString();
    }

    @ShellMethod(key = "post view", value = "view post flow for a postId")
    public String viewPost(
            @ShellOption(value = { "--username", "-u" }, defaultValue = ShellOption.NULL) String username,
            @ShellOption(value = { "--postId", "-p" }, defaultValue = ShellOption.NULL) String postId) {
        log.info("view post request for postId {}", postId);
        Post post = null;
        try {
            post = postService.getPostById(postId);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return "bad request - postId should not be null";
        } catch (Exception e) {
            log.info(e.toString());
            return "internal server error";
        }
        return post.toString();
    }
}
