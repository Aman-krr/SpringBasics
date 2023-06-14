package com.start.SpringBasic.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.start.SpringBasic.support.constant.MessageCode;
import com.start.SpringBasic.support.exception.DuplicateException;
import com.start.SpringBasic.support.exception.NotFoundException;
import com.start.SpringBasic.web.entity.Author;
import com.start.SpringBasic.web.entity.Book;
import com.start.SpringBasic.web.entity.BookAuthor;
import com.start.SpringBasic.web.repository.AuthorRepository;
import com.start.SpringBasic.web.repository.BookAuthorRepository;
import com.start.SpringBasic.web.repository.BookRepository;
import com.start.SpringBasic.web.service.BookService;

/**
 * Book service implementation
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

	private @Autowired BookRepository bookRepository;
	private @Autowired AuthorRepository authorRepository;
	private @Autowired BookAuthorRepository bookAuthorRepository;

	@Override
	public Book getBook(Long bookId) throws NotFoundException {
		Assert.notNull(bookId, "Book ID must not be null");

		Book book = bookRepository.findOne(bookId);
		if (null == book) {
			throw new NotFoundException(MessageCode.BOOK_NOT_FOUND);
		}

		return book;
	}

	@Override
	@Transactional
	public Book createBook(String bookName) throws DuplicateException {
		Assert.hasText(bookName, "Book name must not be empty");

		Book book = bookRepository.findByBookName(bookName);
		if (null != book) {
			throw new DuplicateException(MessageCode.BOOK_DUPLICATE);
		}

		book = new Book();
		book.setBookName(bookName);

		return bookRepository.save(book);
	}

	@Override
	@Transactional
	public void deleteBook(Long bookId) throws NotFoundException {
		Assert.notNull(bookId, "Book ID must not be null");

		Book book = bookRepository.findOne(bookId);
		if (null == book) {
			throw new NotFoundException(MessageCode.BOOK_NOT_FOUND);
		}

		bookAuthorRepository.deleteByBook(book);
		bookRepository.delete(book);
	}

	@Override
	@Transactional
	public Book assignAuthor(Long bookId, Long authorId) throws NotFoundException {
		Assert.notNull(bookId, "Book ID must not be null");
		Assert.notNull(authorId, "Author ID must not be null");

		Book book = bookRepository.findOne(bookId);
		if (null == book) {
			throw new NotFoundException(MessageCode.BOOK_NOT_FOUND);
		}

		Author author = authorRepository.findOne(authorId);
		if (null == author) {
			throw new NotFoundException(MessageCode.AUTHOR_NOT_FOUND);
		}

		BookAuthor bookAuthor = bookAuthorRepository.findByBookAndAuthor(book, author);
		if (null == bookAuthor) {
			bookAuthor = new BookAuthor();
			bookAuthor.setAuthor(author);
			bookAuthor.setBook(book);
			bookAuthorRepository.save(bookAuthor);
		}

		return bookAuthor.getBook();
	}

}
