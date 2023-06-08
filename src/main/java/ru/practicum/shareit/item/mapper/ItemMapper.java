package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemShortResponseDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    public static ItemRequestDto itemToItemRequestDto(Item item) {
        return ItemRequestDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static ItemResponseDto itemToItemResponseDto(Item item, BookingShortDto nextBooking, BookingShortDto lastBooking) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .nextBooking(nextBooking)
                .lastBooking(lastBooking)
                .build();
    }

    public static ItemResponseDto itemToItemResponseDto(Item item, BookingShortDto nextBooking, BookingShortDto lastBooking,
                                                        List<CommentResponseDto> comments) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .nextBooking(nextBooking)
                .lastBooking(lastBooking)
                .comments(comments)
                .build();
    }

    public static ItemShortResponseDto itemToItemShortResponseDto(Item item) {
        return ItemShortResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static List<ItemRequestDto> itemsToItemRequestDtoList(Iterable<Item> items) {
        List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();
        for (Item item : items) {
            itemRequestDtoList.add(itemToItemRequestDto(item));
        }
        return itemRequestDtoList;
    }

    public static List<ItemResponseDto> itemToItemResponseDtoList(List<Item> items) {
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for (Item item : items) {
            itemResponseDtoList.add(itemToItemResponseDto(item, null, null));
        }
        return itemResponseDtoList;
    }

    public static Item itemRequestDtoToItem(ItemRequestDto itemRequestDto, User owner) {
        return Item.builder()
                .name(itemRequestDto.getName())
                .description(itemRequestDto.getDescription())
                .available(itemRequestDto.getAvailable())
                .owner(owner)
                .build();
    }
}