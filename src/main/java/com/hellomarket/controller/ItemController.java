package com.hellomarket.controller;

import com.hellomarket.dto.ItemFormDto;
import com.hellomarket.entity.Item;
import com.hellomarket.service.MemberService;
import com.hellomarket.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;

    /**  상품등록    */
    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        log.info("상품등록get");
        return "item/items";
    }

    @PostMapping("/admin/item/new")
    public String itemNew(@Validated ItemFormDto itemFormDto, BindingResult bindingResult, Model model, MultipartFile imgFile) {

        if (bindingResult.hasErrors()) {
            return "item/items";
        }
        try {
            log.info("POST");
            itemService.saveItem(itemFormDto, imgFile);
            log.info("itemFormDto={}", itemFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            log.info("에러발생");
            return "item/items";
        }
        return "redirect:/";
    }

    /**
     * 상품 수정
     */
    @GetMapping("/admin/item/{itemId}")
    public String itemModifyForm(@PathVariable("itemId") Long itemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
            log.info("상품 수정 - get - 됨?");
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/items";
        }
        return "item/items";
    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, MultipartFile imgFile, Model model) {
        if (bindingResult.hasErrors()) {
            return "item/itemModify";
        }

        try {
            log.info("상품 수정 - post");
            itemService.itemModify(itemFormDto, imgFile);
            log.info("상품 수정 - post 됨?");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/items";
        }
        return "redirect:/";
    }

    /**
     * 상품 상세보기
     */
    @GetMapping("/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "item/itemView";
    }

    /**
     * 상품 관리
     */
    @GetMapping("/admin/item/manage")
    public String itemManage(Model model) {
        List<Item> itemAllList = itemService.allItemView();
        model.addAttribute("itemAllList", itemAllList);
        return "item/itemManage";
    }

    /**
     * 상품 전체 보기
     */
    @GetMapping("/item/list")
    public String itemList(Model model) {
        List<Item> items = itemService.allItemView();
        model.addAttribute("items", items);
        return "item/itemList";
    }

    /** 상품 삭제  */
    @GetMapping("/admin/item/delete/{id}")
    public String itemDtl(Model model, @PathVariable("id") Long id){
        itemService.itemDelete(id);
        return  "redirect:/";
    }
}




