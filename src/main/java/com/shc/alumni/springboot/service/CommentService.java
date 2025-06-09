package com.shc.alumni.springboot.service;


import org.springframework.stereotype.Service;

import com.shc.alumni.springboot.entity.Comment;

import java.util.*;

@Service	
public class CommentService {
    private static List<Comment> commentList = new ArrayList<>();

    public List<Comment> getCommentsByNewsId(int newsId) {
        // Return comments for the specific news ID
        List<Comment> filteredComments = new ArrayList<>();
        for (Comment comment : commentList) {
            if (comment.getNewsId() == newsId) {
                filteredComments.add(comment);
            }
        }
        return filteredComments;
    }

    public void addComment(Comment comment) {
        // Add the comment (in-memory for now)
        comment.setCreatedAt(new Date());
        commentList.add(comment);
    }
}

