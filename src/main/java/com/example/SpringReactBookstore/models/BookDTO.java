package com.example.SpringReactBookstore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private UUID id;

    private String title;

    private String description;

    private int releaseYear;

}
