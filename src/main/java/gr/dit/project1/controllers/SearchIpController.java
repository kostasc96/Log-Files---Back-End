package gr.dit.project1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.dit.project1.entities.Destination;
import gr.dit.project1.entities.Request;
import gr.dit.project1.payload.SearchIpRequest;
import gr.dit.project1.repositories.DestinationRepository;
import gr.dit.project1.repositories.RequestRepository;

@RestController
@RequestMapping("/api/search")
public class SearchIpController {
	
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	DestinationRepository destinationRepository;
	
	@PostMapping
	public ResponseEntity<?> searchIp(@RequestBody SearchIpRequest searchIpRequest) {
		if (searchIpRequest.getSourceIp() == null && searchIpRequest.getDestinationIp() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (searchIpRequest.getSourceIp() != null && searchIpRequest.getDestinationIp() != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (searchIpRequest.getSourceIp() != null) {
			List<Request> list = requestRepository.findAllBySourceIp(searchIpRequest.getSourceIp());
			List<Long> returnList = new ArrayList<>();
			for (Request r : list) {
				returnList.add(r.getId());
			}
			return new ResponseEntity<>(returnList, HttpStatus.OK);
		}
		else {
			List<Destination> listDest = destinationRepository.findAllByDestinationIp(searchIpRequest.getDestinationIp());
			List<Long> returnList = new ArrayList<>();
			for (Destination d : listDest) {
				returnList.add(d.getRequest().getId());
			}
			return new ResponseEntity<>(returnList, HttpStatus.OK);
		}
		
	}

}
