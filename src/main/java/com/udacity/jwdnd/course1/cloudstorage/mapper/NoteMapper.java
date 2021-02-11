package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteByNoteid(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE notetitle = #{notetitle}")
    Note getNoteByNotetitle(String notetitle);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesForUser(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    void update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer noteid);
}
