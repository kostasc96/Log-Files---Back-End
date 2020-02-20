package gr.dit.project1.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.dit.project1.entities.AccessLog;
import gr.dit.project1.entities.Block;
import gr.dit.project1.entities.Destination;
import gr.dit.project1.entities.Request;
import gr.dit.project1.entities.ResponseSize;
import gr.dit.project1.payload.InsertRequest;
import gr.dit.project1.repositories.AccessLogRepository;
import gr.dit.project1.repositories.RequestRepository;
import gr.dit.project1.repositories.ResponseSizeRepository;
import gr.dit.project1.services.FillTablesService;

@RestController
@RequestMapping("/api/new")
public class InsertController {
	
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	AccessLogRepository accessLogRepository;
	
	@Autowired
	ResponseSizeRepository responseSizeRepository; 
	
	@Autowired
    FillTablesService fillTablesService;
	
	@PostMapping
	public ResponseEntity<?> addLog(@RequestBody InsertRequest insertRequest) {
		Request request = new Request();
		if (insertRequest.getSourceIp() == null || insertRequest.getTimestamp() == null || insertRequest.getType() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		request.setSourceIp(insertRequest.getSourceIp());
		request.setType(insertRequest.getType());
		
		if (insertRequest.getTimestamp().contains("T")) {
			insertRequest.setTimestamp(insertRequest.getTimestamp().replace("T", " "));
		}
		insertRequest.setTimestamp(insertRequest.getTimestamp().substring(0, 19));
		LocalDateTime timestamp= fillTablesService.stringToLocalDateTimeRequest(insertRequest.getTimestamp());
		
		request.setTimestamp(timestamp);
		
		ResponseSize size = new ResponseSize();
		if (insertRequest.getSize() != null) {
			size.setSize(Long.parseLong(insertRequest.getSize()));
			size.setRequestId(request);
		}
		
		if (insertRequest.getType().equals("Access")) {
			AccessLog access = new AccessLog();
			access.setMethod(insertRequest.getMethod());
			access.setReferer(insertRequest.getReferer());
			access.setResource(insertRequest.getResource());
			access.setResponse(Long.parseLong(insertRequest.getResponse()));
			access.setUserAgent(insertRequest.getUserAgent());
			access.setUserId(insertRequest.getUserId());
			access.setRequestId(request);
			//maybe save and flush access
			requestRepository.saveAndFlush(request);
			accessLogRepository.saveAndFlush(access);
			if(size.getSize() != null) {
				responseSizeRepository.saveAndFlush(size);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		if (insertRequest.getDestinationIp() != null) {
			List<Destination> destination = new ArrayList<>();
			for (String ds : insertRequest.getDestinationIp()) {
				Destination d = new Destination();
				d.setDestinationIp(ds);
				d.setRequest(request);
				destination.add(d);
			}
			request.setDestinations(destination);
		}
		
		if (insertRequest.getBlockId() != null) {
			List<Block> block = new ArrayList<>();
			for (String bl : insertRequest.getBlockId()) {
				Block b = new Block();
				b.setBlockId(bl);
				b.setRequest(request);
				block.add(b);
			}
			request.setBlocks(block);
		}
		
		requestRepository.saveAndFlush(request);
		if(size.getSize() != null) {
			responseSizeRepository.saveAndFlush(size);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
