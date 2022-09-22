package com.alpha.postandcomments.domain.commands;

import co.com.sofka.domain.generic.Command;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddCommentCommand extends Command {

    private String postId;
    private String commentId;
    private String author;
    private String content;

}
