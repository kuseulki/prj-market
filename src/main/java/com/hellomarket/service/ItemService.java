package com.hellomarket.service;

import com.hellomarket.dto.ItemFormDto;
import com.hellomarket.entity.Item;
import com.hellomarket.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    /**  상품 등록  */
    public void saveItem(ItemFormDto itemFormDto, MultipartFile imgFile) throws Exception {

        String oriImgName = imgFile.getOriginalFilename();
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName
        File saveFile = new File(projectPath, savedFileName);
        imgFile.transferTo(saveFile);
        itemFormDto.setImgName(savedFileName);
        itemFormDto.setImgPath("/files/" + savedFileName);
        Item item = itemFormDto.createItem();
        item.setImgName(itemFormDto.getImgName());
        item.setImgPath(itemFormDto.getImgPath());
        itemRepository.save(item);
    }

    /** 상품 수정 */
    @Transactional
    public void itemModify(ItemFormDto itemFormDto, MultipartFile imgFile) throws Exception {
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        UUID uuid = UUID.randomUUID();
        String modifyFileName = uuid + "_" + imgFile.getOriginalFilename();
        File saveFile = new File(projectPath, modifyFileName);
        imgFile.transferTo(saveFile);

        Item updateItem = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        updateItem.updateItem(itemFormDto);
        updateItem.setImgName(modifyFileName);
        updateItem.setImgPath("/files/"+modifyFileName);
        itemRepository.save(updateItem);
    }

    /**  상품 상세보기  */
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        return itemFormDto;
    }

    // 상품 개별 불러오기
    public Item itemView(Long id) {
        return itemRepository.findById(id).get();
    }

    // 상품 리스트 불러오기
    public List<Item> allItemView() {
        return itemRepository.findAll();
    }

    // 상품 삭제 - cartItem 중에 해당 id 를 가진 item 찾기
    @Transactional
    public void itemDelete(Long id) {
        itemRepository.deleteById(id);
    }
}