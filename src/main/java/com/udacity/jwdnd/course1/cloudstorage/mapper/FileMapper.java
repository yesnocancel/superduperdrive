package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByFilename(String filename);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileByFileid(Integer fileid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFilesForUser(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, " +
                    "#{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    Integer insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void delete(Integer fileid);
}