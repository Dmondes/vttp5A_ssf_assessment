package vttp.batch5.ssf.noticeboard.controllers;

import java.io.StringReader;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.json.JsonObject;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonArray;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers
@Controller
@RequestMapping("/")
public class NoticeController {

    @Autowired
    NoticeService noticeService;
     
    @GetMapping("/")
    public String getNotice(Model model){
        Notice notice = new Notice();
        model.addAttribute("notice", notice);
        return "notice";
    }

    
    @PostMapping("/notice")
    public String postNotice(@Valid @ModelAttribute("notice") Notice entity, BindingResult result, Model model) {

        if (result.hasErrors()){
            return "notice";
        }
        JSONArray catArray = new JSONArray(entity.getCategories());
        String toList = catArray.toString();
        JsonObject jObject = Json.createObjectBuilder()
        .add("title", entity.getTitle())
        .add("poster", entity.getPoster())
        .add("postDate", entity.simpleDueDate())
        .add("categories", toList)
        .add("text", entity.getText())
        .build();
         noticeService.postToNoticeServer(jObject.toString());
        return "redirect:/notice";
    }
     @GetMapping("/notice")
    public ResponseEntity<String> getResponse() {
        JsonObject response = Json.createObjectBuilder()
            .add("message","Your message has been posted to the community message board")
            .add("id", "THe notice posting id is ")
            .build();
        return ResponseEntity.ok().body(response.toString());
    }
     
    
}
