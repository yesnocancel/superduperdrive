package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Authentication authentication) throws IOException {
        User currentUser = userService.getUser(authentication.getName());
        fileService.uploadMultipartFile(file, currentUser.getUserid());

        return "redirect:/home";
    }

    @GetMapping("/delete/{fileid}")
    public String handleFileDelete(@PathVariable Integer fileid, Authentication authentication) {
        User currentUser = userService.getUser(authentication.getName());
        fileService.deleteFile(fileid, currentUser.getUserid());

        return "home";
    }

    @GetMapping("/view/{fileid}")
    public void handleFileView(@PathVariable Integer fileid, HttpServletResponse response, Authentication authentication)
            throws IOException {
        User currentUser = userService.getUser(authentication.getName());
        File file = fileService.getFile(fileid, currentUser.getUserid());

        if (file == null) return;

        response.setContentType(file.getContenttype());
        response.setHeader("Content-Disposition", "filename=\""+ file.getFilename() +"\"");
        response.setContentLengthLong(file.getFilesize());
        OutputStream os = response.getOutputStream();

        try {
            os.write(file.getFiledata(), 0, file.getFiledata().length);
        } catch (Exception e) {
        } finally {
            os.close();
        }
    }
}