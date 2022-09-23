package com.alpha.postandcomments.domain.post.commands;

import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatePostCommand extends Command {
    private String postId;
    private String author;
    private String title;
    private String photoUrl;
    private String participantId;

}
