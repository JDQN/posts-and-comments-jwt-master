package com.alpha.postandcomments.application.adapters.repository;

import com.alpha.postandcomments.application.generic.models.StoredEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentEventStored {
    private String aggregateRootId;

    private StoredEvent storedEvent;
}
