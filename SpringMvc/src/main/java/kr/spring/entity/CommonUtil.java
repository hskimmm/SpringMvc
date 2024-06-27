package kr.spring.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;


public class CommonUtil {
	
	//파일 다운로드
	public void fileDownload(String uploadDir, String filename, HttpServletResponse response) throws IOException{
		//파일 다운로드
		//1.(지정된경로+파일이름)에 해당하는 파일을 File 객체로 읽어온다.
		File file = new File(uploadDir + filename);
		
		//2.읽어온 파일 객체가 존재하는지 확인한다.
		if(!file.exists()) {
			System.out.println("Sorry, the file does not exist.");
		}
		
		//3.File 객체로 읽어온 파일을 FileInputStream 을 통해 파일을 읽는다.
		FileInputStream inputStream = new FileInputStream(file);
		
		//4.HttpServletResponse 을 통해 클라이언트(사용자)에게 파일을 전송한다.
		response.setContentType("application/octet-stream"); //파일의 MIME 타입 설정한다.
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\""); // 파일 다운로드 시 파일 이름을 지정한다.
		
		//5. OutputStream 을 통해 파일의 내용을 클라이언트(사용자)에게 전송한다.
		OutputStream outputStream = response.getOutputStream();
		
		//파일의 데이터를 임시로 저장하는 배열이다. 4096 = 4KB(파일을 작은 덩어리로 나누어 읽고 쓰기 떄문에 버퍼가 필요하다.)
		byte[] buffer = new byte[4096];
		// 초기값 설정
		int bytesRead = -1;
		//파일을 읽어서 buffer에 저장하고 읽은 바이트 수를 bytesRead에 저장한다.
		//파일의 끝에 도달하면 반복문이 종료된다.(-1에 도달하면)
		while((bytesRead = inputStream.read(buffer)) != -1) {
			//outputStream 을 통해 buffer 저장된 데이터를 클라이언트에 전송한다.
			// 0부터 bytesRead 까지의 바이트를 쓴다. 
			outputStream.write(buffer, 0, bytesRead);
		}
		
		//6.파일을 읽기 위해 열었던 스트림을 닫는다.(동작 실행 후 완료 시 꼭 닫아줘야 된다.)
		inputStream.close();
        outputStream.close();	
	}
}
