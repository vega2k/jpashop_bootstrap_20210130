package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.web.BookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
    public List<Item> findItemsGtStock() {
        return itemRepository.findAllGtStock();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    /**
     * 영속성 컨텍스트가 자동 변경
     */
    @Transactional
//    public void updateItem(Long id, String name, int price, int stockQuantity,
//                           String author, String isbn) {
    public void updateItem(BookForm form) {
        Item item = itemRepository.findOne(form.getId());
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        Book book = (Book)item;
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
    }
}
