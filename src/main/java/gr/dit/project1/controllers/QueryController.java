package gr.dit.project1.controllers;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.dit.project1.payload.QueryRequest;
import gr.dit.project1.services.FillTablesService;

@RestController
@RequestMapping("/api/query")
public class QueryController {
	
	@Autowired
    FillTablesService fillTablesService;
	
	@PostMapping
	public ResponseEntity<?> queryExecute(@RequestBody QueryRequest queryRequest) {
		
		if (queryRequest.getStartDate().contains("T")) {
			queryRequest.setStartDate(queryRequest.getStartDate().replace("T", " "));
		}
		queryRequest.setStartDate(queryRequest.getStartDate().substring(0, 19));
		if (!queryRequest.getWord().equals("ThirdQuery")) {
			if (queryRequest.getEndDate().contains("T")) {
				queryRequest.setEndDate(queryRequest.getEndDate().replace("T", " "));
			}
			queryRequest.setEndDate(queryRequest.getEndDate().substring(0, 19));
		}

		LocalDateTime start= fillTablesService.stringToLocalDateTimeRequest(queryRequest.getStartDate());
		LocalDateTime end = null;
		if (!queryRequest.getWord().equals("ThirdQuery")) {
			end = fillTablesService.stringToLocalDateTimeRequest(queryRequest.getEndDate());
		}
		if (queryRequest.getWord().equals("FirstQuery")) {
//			System.out.println("Query1");
//			System.out.println("------------");
//			System.out.println(queryRequest.getStartDate());
//			System.out.println(queryRequest.getEndDate());
//			System.out.println(queryRequest.getWord());
			//System.out.println(queryRequest.getType());
			List<Object[]> list = fillTablesService.executeQuery1(start, end);
			//list.forEach(r -> System.out.println(Arrays.toString(r)));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		else if (queryRequest.getWord().equals("SecondQuery")) {
//			System.out.println("Query2");
//			System.out.println("------------");
//			System.out.println(queryRequest.getStartDate());
//			System.out.println(queryRequest.getEndDate());
//			System.out.println(queryRequest.getWord());
//			System.out.println(queryRequest.getType());
			List<Object[]> list = fillTablesService.executeQuery2(start, end, queryRequest.getType());
			//list.forEach(r -> System.out.println(Arrays.toString(r)));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		else {
			//System.out.println("Query3");
			//System.out.println("------------");
			//System.out.println(queryRequest.getStartDate());
			//System.out.println(queryRequest.getEndDate());
			//System.out.println(queryRequest.getWord());
			//System.out.println(queryRequest.getType());
			List<Object[]> list = fillTablesService.executeQuery3(start);
			//list.forEach(r -> System.out.println(Arrays.toString(r)));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

}
