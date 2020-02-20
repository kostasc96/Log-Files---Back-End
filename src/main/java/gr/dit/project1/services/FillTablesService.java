package gr.dit.project1.services;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import gr.dit.project1.entities.AccessLog;
import gr.dit.project1.entities.Block;
import gr.dit.project1.entities.Destination;
import gr.dit.project1.entities.Request;
import gr.dit.project1.entities.ResponseSize;
import gr.dit.project1.repositories.AccessLogRepository;
import gr.dit.project1.repositories.RequestRepository;
import gr.dit.project1.repositories.ResponseSizeRepository;

@Service
public class FillTablesService {

  static final Logger logger = LoggerFactory.getLogger(FillTablesService.class);

  private final RequestRepository requestRepository;
  private final AccessLogRepository accessLogRepository;
  private final ResponseSizeRepository responseSizeRepository;

  public FillTablesService(RequestRepository requestRepository,
      AccessLogRepository accessLogRepository, ResponseSizeRepository responseSizeRepository) {
    this.requestRepository = requestRepository;
    this.accessLogRepository = accessLogRepository;
    this.responseSizeRepository = responseSizeRepository;
  }

  public void parseAccessLog() throws IOException {
    ClassPathResource classPathResource = new ClassPathResource("access.log");
    try (BufferedReader br =
        new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {

      String strLine;
      int val = 1;
      while ((strLine = br.readLine()) != null) {
        String[] data = strLine.split(" ");
        Request request = new Request();
        AccessLog accessLog = new AccessLog();
        ResponseSize responseSize = new ResponseSize();
        // System.out.println("length: " + data.length);
        if (data[7].equals("")) {
          List<String> list = new ArrayList<String>(Arrays.asList(data));
          list.remove(7);
          Object[] data1 = list.toArray();
          int length = data1.length;
          data1[11] = ((String) data1[11]).substring(1);
          data1[length - 2] =
              ((String) data1[length - 2]).substring(0, ((String) data1[length - 2]).length() - 1);

          String ipAddress = (String) data1[0];
          request.setSourceIp(ipAddress);
          // System.out.println(ipAddress);
          String userId = (String) data1[2];
          if (userId.equals("-")) {
            accessLog.setUserId(null);
          } else {
            // System.out.println(userId);
            accessLog.setUserId(userId);
          }
          String stringTimestamp =
              strLine.substring(strLine.indexOf("[") + 1, strLine.indexOf("]"));
          LocalDateTime timestamp = stringToLocalDateTimeAccessLog(stringTimestamp);
          request.setTimestamp(timestamp);
          // System.out.println(timestamp);
          String method = (String) data1[5];
          method = method.substring(1);
          if (method.length() > 10) {
        	  continue;
          }
          accessLog.setMethod(method);
          // System.out.println(method);
          String resource = (String) data1[6];
          accessLog.setResource(resource);
          // System.out.println(resource);
          if (data1[8].equals("-")) {
            accessLog.setResponse(null);
          } else {
            Long responseStatus = Long.parseLong((String) data1[8]);
            accessLog.setResponse(responseStatus);
            // System.out.println(responseStatus);
          }
          if (data1[9].equals("-")) {
        	  responseSize.setSize(null);
          } else {
            Long rSize = Long.parseLong((String) data1[9]);
            responseSize.setSize(rSize);
            // System.out.println(responseSize);
          }
          String referer = (String) data1[10];
          referer = referer.substring(1);
          referer = referer.substring(0, referer.length() - 1);
          if (referer.equals("-")) {
            accessLog.setReferer(null);
          } else {
            accessLog.setReferer(referer);
            // System.out.println(referer);
          }
          StringBuilder userAgentBuilder = new StringBuilder();
          for (int i = 11; i < data1.length - 1; i++) {
            userAgentBuilder.append(data1[i]);
            if (i != data1.length - 2) {
              userAgentBuilder.append(" ");
            }
          }
          String userAgent = userAgentBuilder.toString();
          if (userAgent.equals("-")) {
            accessLog.setUserAgent(null);
          } else {
            accessLog.setUserAgent(userAgent);
            // System.out.println(userAgent);
          }
          accessLog.setRequestId(request);
          System.out.println("row = " + val);
          System.out.println("---------------");
          val++;
          requestRepository.saveAndFlush(request);
          if (responseSize.getSize() != null) {
              responseSize.setRequestId(request);
              responseSizeRepository.saveAndFlush(responseSize);
          }
          accessLogRepository.saveAndFlush(accessLog);
        } else {
          int length = data.length;
          data[11] = data[11].substring(1);
          data[length - 2] = data[length - 2].substring(0, data[length - 2].length() - 1);
          String ipAddress = data[0];
          request.setSourceIp(ipAddress);
          // System.out.println(ipAddress);
          String userId = data[2];
          if (userId.equals("-")) {
            accessLog.setUserId(null);
          } else {
            accessLog.setUserId(userId);
            // System.out.println(userId);
          }
          String stringTimestamp =
              strLine.substring(strLine.indexOf("[") + 1, strLine.indexOf("]"));
          LocalDateTime timestamp = stringToLocalDateTimeAccessLog(stringTimestamp);
          request.setTimestamp(timestamp);
          // System.out.println(timestamp);
          String method = data[5];
          method = method.substring(1);
          if (method.length() > 10) {
        	  continue;
          }
          accessLog.setMethod(method);
          // System.out.println(method);
          String resource = data[6];
          accessLog.setResource(resource);
          // System.out.println(resource);
          if (data[8].equals("-")) {
            accessLog.setResponse(null);
          } else {
            long responseStatus = Long.parseLong(data[8]);
            accessLog.setResponse(responseStatus);
            // System.out.println(responseStatus);
          }
          if (data[9].equals("-")) {
        	  responseSize.setSize(null);
          } else {
            long rSize = Long.parseLong(data[9]);
            responseSize.setSize(rSize);
            // System.out.println(responseSize);
          }
          String referer = data[10];
          referer = referer.substring(1);
          referer = referer.substring(0, referer.length() - 1);
          if (referer.equals("-")) {
            accessLog.setReferer(null);
          } else {
            accessLog.setReferer(referer);
            // System.out.println(referer);
          }
          StringBuilder userAgentBuilder = new StringBuilder();
          for (int i = 11; i < data.length - 1; i++) {
            userAgentBuilder.append(data[i]);
            if (i != data.length - 2) {
              userAgentBuilder.append(" ");
            }
          }
          String userAgent = userAgentBuilder.toString();
          if (userAgent.equals("-")) {
            accessLog.setUserAgent(null);
          } else {
            accessLog.setUserAgent(userAgent);
            // System.out.println(userAgent);
          }
          accessLog.setRequestId(request);
          System.out.println("row = " + val);
          System.out.println("---------------");
          val++;
          requestRepository.saveAndFlush(request);
          if (responseSize.getSize() != null) {
              responseSize.setRequestId(request);
              responseSizeRepository.saveAndFlush(responseSize);
          }
          accessLogRepository.saveAndFlush(accessLog);
        }
      }
    } catch (Exception e) {
      logger.error("Error adding row", e);
    }

  }


  public LocalDateTime stringToLocalDateTimeAccessLog(String date) {
    try {
      DateTimeFormatter sdf =
          DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);
      LocalDateTime timeWithZone = LocalDateTime.parse(date, sdf);
      return timeWithZone;
    } catch (DateTimeParseException exception) {
      logger.error("Error parsing Date ", exception);
      return null;
    }
  }
  
  public LocalDateTime stringToLocalDateTimeRequest(String date) {
	    try {
	      DateTimeFormatter sdf =
	          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	      LocalDateTime timeWithZone = LocalDateTime.parse(date, sdf);
	      return timeWithZone;
	    } catch (DateTimeParseException exception) {
	      logger.error("Error parsing Date ", exception);
	      return null;
	    }
	  }

  public LocalDateTime stringToLocalDateTimeDataXceiver(String date) {
    try {
      DateTimeFormatter sdf = DateTimeFormatter.ofPattern("ddMMyyHHmmss");
      LocalDateTime timeWithZone = LocalDateTime.parse(date, sdf);
      return timeWithZone;
    } catch (DateTimeParseException exception) {
      logger.error("Error parsing Date ", exception);
      return null;
    }
  }

  public void parseDataXceiver() throws IOException {
    ClassPathResource classPathResource = new ClassPathResource("HDFS_DataXceiver.log");
    try (BufferedReader br =
        new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {
      String strLine;
      int c = 0;
      while ((strLine = br.readLine()) != null) {
        Request request = new Request();
        Block block = new Block();
        Destination destination = new Destination();
        ResponseSize responseSize = new ResponseSize();
        System.out.println(c);
        c++;
        String[] data = strLine.split(" ");
        boolean flag = false;
        for (String s : data) {
          if (s.equals("exception")) {
            flag = true;
            break;
          }
        }
        if (flag == true) {
          continue;
        }
        if (data[5].equals("writeBlock")) {
          continue;
        }
        if (data[3].equals("ERROR")) {
          continue;
        }
        String stringTimestamp = data[0] + data[1];
        LocalDateTime timestamp = stringToLocalDateTimeDataXceiver(stringTimestamp);
        // System.out.println(timestamp);
        request.setTimestamp(timestamp);
        List<Block> blocks = new ArrayList<>();
        for (String s : data) {
          if (s.startsWith("blk_")) {
            // System.out.println(s);
            block.setBlockId(s);
            block.setRequest(request);
            blocks.add(block);
            request.setBlocks(blocks);
            break;
          }
        }
        int srcPosition = 0;
        int destPosition = 0;
        for (String s : data) {
          if (s.equals("src:")) {
            break;
          }
          srcPosition++;
        }
        for (String s : data) {
          if (s.equals("dest:")) {
            break;
          }
          destPosition++;
        }
        List<Destination> destinations = new ArrayList<>();
        // if src is not exist
        if (srcPosition == data.length) {
          String ipAddress = data[5];
          if (ipAddress.startsWith("/")) {
            ipAddress = ipAddress.substring(1);
          }
          if (ipAddress.contains(":")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(":"));
          }
          // System.out.println(ipAddress);
          request.setSourceIp(ipAddress);
          int destPos = 0;
          for (String s : data) {
            if (s.equals("to")) {
              break;
            }
            destPos++;
          }
          String destAddress = data[destPos + 1];
          if (destAddress.startsWith("/")) {
            destAddress = destAddress.substring(1);
          }
          if (destAddress.contains(":")) {
            destAddress = destAddress.substring(0, destAddress.indexOf(":"));
          }
          // System.out.println(destAddress);
          destination.setDestinationIp(destAddress);
          if (data[3].equals("INFO")) {
            //String type = data[6];
            // System.out.println(type);
            //requestType.setType(type);
          } else {
            //requestType.setType(null);
          }
          if (data[data.length - 2].equals("size")) {
            Long size = Long.parseLong(data[data.length - 1]);
            responseSize.setSize(size);
            // System.out.println(size);
          } else {
        	  responseSize.setSize(null);
            // System.out.println("Does not exists");
          }
          destination.setRequest(request);
          destinations.add(destination);
          request.setDestinations(destinations);
        }
        // if src exist
        else {
          String ipAddress = data[srcPosition + 1];
          String destAddress = data[destPosition + 1];
          if (ipAddress.startsWith("/")) {
            ipAddress = ipAddress.substring(1);
          }
          if (ipAddress.contains(":")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(":"));
          }
          if (destAddress.startsWith("/")) {
            destAddress = destAddress.substring(1);
          }
          if (destAddress.contains(":")) {
            destAddress = destAddress.substring(0, destAddress.indexOf(":"));
          }
          request.setSourceIp(ipAddress);
          // System.out.println(ipAddress);
          destination.setDestinationIp(destAddress);
          // System.out.println(destAddress);
          //String type = data[5];
          //requestType.setType(type);
          // System.out.println(type);
          if (data[data.length - 2].equals("size")) {
            Long size = Long.parseLong(data[data.length - 1]);
            responseSize.setSize(size);
            // System.out.println(size);
          } else {
        	  responseSize.setSize(null);
            // System.out.println("Does not exists");
          }
          destination.setRequest(request);
          destinations.add(destination);
          request.setDestinations(destinations);
        }
        requestRepository.saveAndFlush(request);
        //if (requestType.getType() != null) {
        //  requestType.setRequestId(request);
        //  requestTypeRepository.saveAndFlush(requestType);
        //}
        if (responseSize.getSize() != null) {
        	responseSize.setRequestId(request);
        	responseSizeRepository.saveAndFlush(responseSize);
        }
        System.out.println("------------------");
      }
    } catch (Exception e) {
      logger.error("Error adding row", e);
    }
  }

  public void parseNameSystem() throws IOException {
    ClassPathResource classPathResource = new ClassPathResource("HDFS_FS_Namesystem.log");
    try (BufferedReader br =
        new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {
      String strLine;
      int lines = 0;
      while ((strLine = br.readLine()) != null) {
        lines++;
        Request request = new Request();
        // get timestamp
        String[] data = strLine.split(" ");
        String stringTimestamp = data[0] + data[1];
        LocalDateTime timestamp = stringToLocalDateTimeDataXceiver(stringTimestamp);
        // System.out.println(timestamp);
        request.setTimestamp(timestamp);
        String sourceIp = data[7];
        if (sourceIp.contains(":")) {
          sourceIp = sourceIp.substring(0, sourceIp.indexOf(":"));
        }
        // System.out.println(sourceIp);
        request.setSourceIp(sourceIp);
        // get dest ip
        boolean flag = false;
        int ipPos2 = 0;

        // get type
        //String type = data[9];
        // System.out.println(type);
        
        //requestType.setType(type);

        for (String s : data) {
          if (s.equals("datanode(s)")) {
            flag = true;
            break;
          }
          ipPos2++;
        }
        if (flag == true) {
          List<Destination> destinations = new ArrayList<>();
          for (int i = ipPos2 + 1; i < data.length; i++) {
            Destination destination = new Destination();
            String destIp = data[i];
            destination.setDestinationIp(destIp);
            destination.setRequest(request);
            destinations.add(destination);
            // System.out.println(destIp);
          }
          request.setDestinations(destinations);
        } else {
          // null to dest ip
          // System.out.println("No dest IP");
        }

        // get block(s)
        List<Block> blocks = new ArrayList<>();
        for (String s : data) {
          if (s.contains("blk_")) {
            Block block = new Block();
            block.setBlockId(s);
            block.setRequest(request);
            blocks.add(block);
            // System.out.println(s);
          }
        }
        if (blocks.size() > 0) {
          request.setBlocks(blocks);
        }

        requestRepository.saveAndFlush(request);
        System.out.println(lines);
        System.out.println("--------------------");
      }
    } catch (Exception e) {
      logger.error("Error adding row", e);
    }
  }
  
  public List<Object[]> executeQuery1(LocalDateTime start, LocalDateTime end) {
	  //LocalDateTime start  = LocalDateTime.of(2016, Month.JULY, 29, 19, 30, 40);
	  //LocalDateTime end  = LocalDateTime.of(2018, Month.JULY, 29, 19, 30, 40);
	  List<Object[]> resultList = requestRepository.query1(start, end);
	  //resultList.forEach(r -> System.out.println(Arrays.toString(r)));
	  return resultList;
  }
  
  public List<Object[]> executeQuery2(LocalDateTime start, LocalDateTime end, String type) {
	  //String type = "Access";
	  //LocalDateTime start  = LocalDateTime.of(2000, Month.JULY, 29, 19, 30, 40);
	  //LocalDateTime end  = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40);
	  List<Object[]> resultList = requestRepository.query2(type, start, end);
	  //resultList.forEach(r -> System.out.println(Arrays.toString(r)));
	  return resultList;
  }
  
  public List<Object[]> executeQuery3(LocalDateTime start) {
	  //LocalDateTime specificDay  = LocalDateTime.of(2018, Month.NOVEMBER, 8, 19, 30, 40);
	  List<Object[]> list = requestRepository.query3(start);
	  if(list.isEmpty()) {
		  return null;
	  }
	  List<Object[]> returnList = new ArrayList<>();
	  Object[] returnObject = new Object[3];
	  String previousIp = list.get(0)[0].toString();
	  //System.out.println(previousIp + " " + resultList.get(0)[1].toString() + " " + resultList.get(0)[2].toString());
	  returnObject[0] = previousIp;
	  returnObject[1] = list.get(0)[1].toString();
	  returnObject[2] = list.get(0)[2].toString();
	  returnList.add(returnObject);
	  for (Object[] r : list) {
		  String sourceIp = r[0].toString();
		  if (sourceIp.equals(previousIp)) {
			  continue;
		  }
		  Object[] returnObject1 = new Object[3];
		  returnObject1[0] = r[0];
		  returnObject1[1] = r[1];
		  returnObject1[2] = r[2];
		  returnList.add(returnObject1);
		  //System.out.println(r[0] + " " + r[1] + " " + r[2]);
		  previousIp = sourceIp;
	  }
	  //resultList.forEach(r -> System.out.println(Arrays.toString(r)));
	  return returnList;
  }

}
