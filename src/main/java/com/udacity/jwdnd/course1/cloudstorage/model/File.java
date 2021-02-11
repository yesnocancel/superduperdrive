package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class File {
    private Integer fileid;
    private String filename;
    private String contenttype;
    private Long filesize;
    private Integer userid;
    private byte[] filedata;
}
