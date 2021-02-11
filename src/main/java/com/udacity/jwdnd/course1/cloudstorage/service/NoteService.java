package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNoteListForUser(Integer userId) {
        return noteMapper.getNotesForUser(userId);
    }

    public void saveNote(Integer noteid, String notetitle, String notedescription, Integer userId) {
        Note note = new Note(noteid, notetitle, notedescription, userId);

        if (noteid == null) {
            noteMapper.insert(note);
        } else {
            noteMapper.update(note);
        }
    }

    public void deleteNote(Integer noteid, Integer userId) {
        // Make sure that we delete a note only if the user id matches!
        Note note = noteMapper.getNoteByNoteid(noteid);

        if (note != null && note.getUserid() == userId) {
            noteMapper.delete(noteid);
        }
    }
}
