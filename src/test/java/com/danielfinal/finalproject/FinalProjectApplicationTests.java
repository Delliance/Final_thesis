package com.danielfinal.finalproject;

import com.danielfinal.finalproject.entities.Attachment;
import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.entities.Tag;
import com.danielfinal.finalproject.messageattachment.ResponseFile;
import com.danielfinal.finalproject.messageattachment.ResponseMessage;
import com.danielfinal.finalproject.registration.RegistrationRequest;
import com.danielfinal.finalproject.registration.RegistrationService;
import com.danielfinal.finalproject.repositories.UserRepository;
import com.danielfinal.finalproject.services.AttachmentService;
import com.danielfinal.finalproject.services.MailboxService;
import com.danielfinal.finalproject.services.TagService;
import com.danielfinal.finalproject.services.UserService;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@WithMockUser(username = "DGonzalez", roles = "USER")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FinalProjectApplicationTests {

//Notice that here we use a mock user DGonzalez, this is similar to the one that is logged in
//	with spring security

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private MailboxService mailboxService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private TagService tagService;

	@BeforeAll
	public void mockUser(){

		RegistrationRequest request =
				RegistrationRequest.builder()
						.firstName("Daniel")
						.lastName("Gonzalez")
						.username("DGonzalez")
						.password("12345")
						.dni("1234567899ABCD")
						.address("km-1")
						.zipCode(null)
						.city("Cali")
						.state("Valle")
						.country("Colombia")
						.build();

		registrationService.register(request);

	}

	@Nested
	class TestRegistration{

		@Test
		public void validRegistrationNewUser(){

			RegistrationRequest request =
					RegistrationRequest.builder()
							.firstName("Daniel")
							.lastName("Gonzalez")
							.username("DGonzalez9999")
							.password("12345")
							.dni("1234567899")
							.address("km-1")
							.zipCode(null)
							.city("Cali")
							.state("Valle")
							.country("Colombia")
							.build();

			registrationService.register(request);

		}

		@Test
		public void invalidRegistrationUserAlreadyTaken(){

			RegistrationRequest request =
					RegistrationRequest.builder()
							.firstName("Daniel")
							.lastName("Gonzalez")
							.username("DGonzalez2")
							.password("12345")
							.dni("4567")
							.address("km-1")
							.zipCode(null)
							.city("Cali")
							.state("Valle")
							.country("Colombia")
							.build();

			RegistrationRequest request2 =
					RegistrationRequest.builder()
							.firstName("Daniela")
							.lastName("Gonzalez")
							.username("DGonzalez2")
							.password("6789")
							.dni("dwed933f")
							.address("km-1")
							.zipCode(null)
							.city("Cali")
							.state("Valle")
							.country("Colombia")
							.build();

			registrationService.register(request);

			RuntimeException exception = assertThrows(RuntimeException.class, () ->
					registrationService.register(request2));

			System.out.println("exception = " + exception);

		}

		@Test
		public void invalidRegistrationDniAlreadyExists(){

			RegistrationRequest request =
					RegistrationRequest.builder()
							.firstName("Jaime")
							.lastName("Zapata")
							.username("Jaime1990")
							.password("12345")
							.dni("111555444")
							.address("nose")
							.zipCode(null)
							.city("Palmira")
							.state("Valle")
							.country("Colombia")
							.build();

			RegistrationRequest request2 =
					RegistrationRequest.builder()
							.firstName("Jaime")
							.lastName("Zapata")
							.username("JaimeZapata90")
							.password("12345")
							.dni("111555444")
							.address("nose")
							.zipCode(null)
							.city("Palmira")
							.state("Valle")
							.country("Colombia")
							.build();

			registrationService.register(request);

			RuntimeException exception = assertThrows(RuntimeException.class, () ->
					registrationService.register(request2));

			System.out.println("exception = " + exception);

		}

	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class TestMessaging{



		@Test
		public void validSendNewMessage(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez")
							.build();

			mailboxService.sendMessage(message);

		}

		@Test
		public void validSendNewMessageWithCopies(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez")
							.carbonCopy("DGonzalez")
							.blindCarbonCopy("DGonzalez")
							.build();

			mailboxService.sendMessage(message);

		}

		@Test
		public void invalidSendNewMessagePrimaryReceptorDoesNotExist(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez1")
							.carbonCopy("DGonzalez")
							.blindCarbonCopy("DGonzalez")
							.build();
			RuntimeException exception = assertThrows(RuntimeException.class, () ->
					mailboxService.sendMessage(message));

			System.out.println("exception = " + exception);

		}

		@Test
		public void invalidSendNewMessageCarbonCopyDoesNotExist(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez")
							.carbonCopy("DGonzalez1")
							.blindCarbonCopy("DGonzalez")
							.build();
			RuntimeException exception = assertThrows(RuntimeException.class, () ->
					mailboxService.sendMessage(message));

			System.out.println("exception = " + exception);

		}

		@Test
		public void invalidSendNewMessageBlindCarbonCopyDoesNotExist(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez")
							.carbonCopy("DGonzalez")
							.blindCarbonCopy("DGonzalez1")
							.build();
			RuntimeException exception = assertThrows(RuntimeException.class, () ->
					mailboxService.sendMessage(message));

			System.out.println("exception = " + exception);

		}

	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class TestMailBox{

		@Test
		public void getAllReceivedEmails(){
			mailboxService.getReceivedMessagesByMailBox();
		}

		@Test
		public void getAllSentEmails(){
			mailboxService.getSentMessagesByMailBox();
		}

	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class TestAttachment{

		@Test
		public void uploadAttachment(){

//			String message;
//			try {
//				attachmentService.store(file,messageId);
//				message = "File "+ file.getOriginalFilename() +"attached successfully to the email";
//				ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//			}
//			catch (Exception e){
//				message = "Could not upload the file";
//				ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//			}

		}

		@Test
		public void getAttachmentListByReceivedEmail(){
//			List<ResponseFile> files = attachmentService.getFilesByEmailId(messageId).map(dbFile -> {
//				String fileDownloadUri = ServletUriComponentsBuilder
//						.fromCurrentContextPath()
//						.path("/received/{messageId}/")
//						.path(dbFile.getId())
//						.toUriString();
//				return new ResponseFile(
//						dbFile.getName(),
//						fileDownloadUri,
//						dbFile.getType(),
//						dbFile.getData().length);
//			}).collect(Collectors.toList());
//			ResponseEntity.status(HttpStatus.OK).body(files);
		}

		@Test
		public void getAttachmentById(){
//			Attachment attachment = attachmentService.getFiles(id);
//			ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName()+"\"")
//					.body(attachment.getData());
		}

	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class TestTagging{

		@Test
		public void validCreateNewTag(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez")
							.carbonCopy("DGonzalez")
							.blindCarbonCopy("DGonzalez")
							.build();

			mailboxService.sendMessage(message);

			Tag tag =
					Tag.builder()
							.name("Testing1")
							.message(message)
							.build();

			tagService.createNewTag(tag);

		}

		@Test
		public void invalidCreateNewTagNameAlreadyExists(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez")
							.carbonCopy("DGonzalez")
							.blindCarbonCopy("DGonzalez")
							.build();

			mailboxService.sendMessage(message);

			Tag tag =
					Tag.builder()
							.name("Testing2")
							.message(message)
							.build();


			Tag tag2 =
					Tag.builder()
							.name("Testing2")
							.message(message)
							.build();

			tagService.createNewTag(tag);

			RuntimeException exception = assertThrows(RuntimeException.class, () ->
					tagService.createNewTag(tag2));

		}

		@Test
		public void getMessagesByTag(){

			Message message =
					Message.builder()
							.subject("Hello, and greetings")
							.body("Hello Sir, I just wanted to let you know that....")
							.primaryReceptor("DGonzalez")
							.carbonCopy("DGonzalez")
							.blindCarbonCopy("DGonzalez")
							.build();

			mailboxService.sendMessage(message);

			Tag tag =
					Tag.builder()
							.name("Testing3")
							.message(message)
							.build();

			tagService.createNewTag(tag);

			tagService.getMessagesByTagName(tag.getName());

		}



	}



}
