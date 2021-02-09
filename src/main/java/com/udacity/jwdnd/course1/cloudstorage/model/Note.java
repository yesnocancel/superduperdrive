package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Note {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;
}
