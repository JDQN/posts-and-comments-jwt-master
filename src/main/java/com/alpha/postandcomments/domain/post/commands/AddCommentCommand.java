package com.alpha.postandcomments.domain.post.commands;

import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentCommand extends Command {

    private String postId;
    private String commentId;
    private String author;
    private String content;
    private String participantId;

}
