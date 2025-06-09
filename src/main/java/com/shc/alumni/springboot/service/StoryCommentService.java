
package com.shc.alumni.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

//import com.shc.alumni.springboot.entity.Comment;
import com.shc.alumni.springboot.entity.StoryComment;

@Service
public class StoryCommentService {
	
	
	private static List<StoryComment> storycommentList = new ArrayList<>();

    public List<StoryComment> getCommentsByStoryId(int storyId) {
        // Return comments for the specific news ID
        List<StoryComment> filteredComments = new ArrayList<>();
        for (StoryComment storycomment : storycommentList) {
            if (storycomment.getStoryId() == storyId) {
                filteredComments.add(storycomment);
            }
        }
        return filteredComments;
    }

    public void addComment(StoryComment storycomment) {
        // Add the comment (in-memory for now)
    	storycomment.setCreatedAt(new Date());
    	storycommentList.add(storycomment);
    }
    
    
    

}
