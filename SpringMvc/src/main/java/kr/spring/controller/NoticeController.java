package kr.spring.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Board;
import kr.spring.entity.FileUpload;
import kr.spring.entity.Member;
import kr.spring.entity.MemberAuth;
import kr.spring.mapper.NoticeMapper;

@Controller
public class NoticeController {
	
	private final String uploadDir = "C:/upload/";
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	
	/**
	 * @apiNote 공지사항 페이지 이동
	 * @author hskim
	 * @since 2024-06-24
	 * @param model
	 * @return
	 */
	@RequestMapping("/noticeMain.do")
	public String newNotice(Model model, HttpSession session) {
		try {
			//공지사항 목록 조회
			List<Board> noticeList = noticeMapper.noticeList();
			model.addAttribute("noticeList", noticeList);
			
			//권한 처리
			Member member = (Member) session.getAttribute("member");
			List<MemberAuth> memberAuthlist = member.getMemberAuthList();
			
			for(MemberAuth auth : memberAuthlist) {
				if(auth.getAuth().equals("ROLE_ADMIN")) {
					session.setAttribute("noticeAuth", auth.getAuth());
					model.addAttribute("memberAuth", auth.getAuth());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "notice/notice_list";
	}
	
	
	/**
	 * @apiNote 공지사항 생성 페이지 이동
	 * @author hskim
	 * @since 2024-06-24
	 * @return
	 */
	@RequestMapping("/createNoticeForm.do")
	public String createNoticeForm() {
		return "notice/create_notice";
	}
	
	
	/**
	 * @apiNote 공지사항 생성
	 * @author hskim
	 * @since 2024-06-26
	 * @param notice
	 * @param file
	 * @param memId
	 * @param redirect
	 * @param model
	 */
	@RequestMapping("/noticeCreate.do")
	public @ResponseBody void createNotice(Board notice, MultipartFile file, String memId, RedirectAttributes redirect, Model model) {
		try {
			//공지사항 생성
			notice.setWriter(memId);

			int result = noticeMapper.createNotice(notice);
			
			if(result > 0) {
				redirect.addFlashAttribute("messageType", "success");
				redirect.addFlashAttribute("message", "공지사항이 등록되었습니다.");
			} else {
				redirect.addFlashAttribute("messageType", "error");
				redirect.addFlashAttribute("message", "공지사항 등록 중 에러가 발생하였습니다.");
			}
			
			//파일 업로드
			if(!file.isEmpty()) {
				//파일 지정 경로에 저장
				String originalFileName = file.getOriginalFilename();
				int index = originalFileName.lastIndexOf('.');
				String fileExt = originalFileName.substring(index + 1).toLowerCase();
				
				if(fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png") || fileExt.equals("gif")) {
					File dir = new File(uploadDir);
					
					
					if(!dir.exists()) {
						dir.mkdirs();
					}
					
					String filePath = dir + File.separator + System.currentTimeMillis() + "-" + originalFileName;
					String saveFileName = filePath.substring("C:\\upload\\".length());
					
					File dest = new File(filePath);
					file.transferTo(dest);
					
					//DB 저장
					FileUpload fileUpload = new FileUpload();
					fileUpload.setFilename(originalFileName);
					fileUpload.setSavefilename(saveFileName);
					fileUpload.setFilepath(filePath);
					fileUpload.setBoard_id(notice.getIdx());
					
					noticeMapper.createFile(fileUpload);
				} else {
					throw new Exception("이미지 파일만 업로드 가능합니다.");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @apiNote 공지사항 상세 페이지 이동
	 * @author hskim
	 * @since 2024-06-24
	 * @param idx
	 * @param model
	 * @return
	 */
	@RequestMapping("/noticeDetail.do")
	public String noticeDetail(@RequestParam int idx, Model model, HttpSession session) {
		try {
			//조회수 증가
			noticeMapper.noticeCount(idx);
			
			//상세 페이지 조회
			Board noticeDetail = noticeMapper.noticeDetail(idx);
			model.addAttribute("notice", noticeDetail);
			
			//파일 가져오기
			FileUpload file = noticeMapper.fileDetail(idx);
			model.addAttribute("file", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "notice/notice_detail";
	}
	
	
	/**
	 * @apiNote 공지사항 삭제
	 * @author hskim
	 * @since 2024-06-24
	 * @param idx
	 * @param redirect
	 */
	@RequestMapping("/noticeDelete.do")
	public @ResponseBody void noticeDelete(@RequestParam int idx, RedirectAttributes redirect) {
		try {
			int result = noticeMapper.noticeDelete(idx);
			
			if(result > 0) {
				redirect.addFlashAttribute("messageType", "success");
				redirect.addFlashAttribute("message", "공지사항을 삭제하였습니다.");
			} else {
				redirect.addFlashAttribute("meesageType", "error");
				redirect.addFlashAttribute("message", "공지사항 삭제에 실패하였습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @apiNote 공지사항 수정 페이지로 이동
	 * @author hskim
	 * @since 2024-06-25
	 * @param idx
	 * @param model
	 * @return
	 */
	@RequestMapping("/noticeUpdateForm.do")
	public String noticeUpdateForm(@RequestParam int idx, Model model) {
		Board noticeDetail = noticeMapper.noticeDetail(idx);
		model.addAttribute("notice", noticeDetail);
		
		return "notice/notice_update";
	}
	
	
	/**
	 * @apiNote 공지사항 수정
	 * @author hskim
	 * @since 2024-06-25
	 * @param notice
	 * @param redirect
	 */
	@RequestMapping("/noticeUpdate.do")
	public @ResponseBody void noticeUpdate(Board notice, RedirectAttributes redirect) {
		try {
			int updateResult = noticeMapper.noticeUpdate(notice);
			
			if(updateResult > 0) {
				redirect.addFlashAttribute("messageType", "success");
				redirect.addFlashAttribute("message", "공지사항을 수정하였습니다.");
			} else {
				redirect.addFlashAttribute("messageType", "error");
				redirect.addFlashAttribute("message", "공지사항 수정에 실패하였습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/noticeFileDownload.do")
	public @ResponseBody void noticeFileDownload(String filename, HttpServletRequest request, HttpServletResponse response) {
		
		try {
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
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to download file: " + e.getMessage());
		}
		
	}
}
