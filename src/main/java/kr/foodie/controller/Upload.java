package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.user.User;
import kr.foodie.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;



@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/auth")
public class Upload {

    private final String server = "foodie.speedgabia.com";
    private int port = 21;
    private final String user = "foodie";
    private final String pw = "a584472yscp@@";

    public void ftpTest(MultipartFile[] files) throws Throwable {
        //받는 변수는 request를 보낸 것에 맞게 받으시면 됩니다.
        //웹에서 받은 MultipartFile을 File로 변환시켜줍니다.
        File convertFile = new File(String.valueOf(files[0]));
        convertFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertFile);
//        fos.write(uploadFile.get(0).getBytes());
        fos.close();
        try {
            //FTPClient를 생성합니다.
            FTPClient ftp = new FTPClient();
            FileInputStream fis = null;
            try {
                //원하시는 인코딩 타입
                ftp.setControlEncoding("utf-8");
                ftp.connect(server,port);
                ftp.login(user,pw);
                //원하시는 파일 타입
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                //제대로 연결이 안댔을 경우 ftp접속을 끊습니다.
                if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                    ftp.disconnect();
                }
                //파일을 넣을 디렉토리를 설정해줍니다.
                //makeDirectory는 directory 생성이 필요할 때만 해주시면 됩니다.
//                ftp.makeDirectory(directoryRoot);
//                ftp.changeWorkingDirectory(directoryRoot);
                //그 후 이전에 File로 변환한 업로드파일을 읽어 FTP로 전송합니다.
                fis = new FileInputStream(convertFile);
                boolean isSuccess = ftp.storeFile("fileName", fis);
                //storeFile Method는 파일 송신결과를 boolean값으로 리턴합니다
                if(isSuccess) {
                    System.out.println("업로드 성공");
                } else {
                    System.out.println("업로드 실패");
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if(fis!=null) {
                    try {
                        fis.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
//            if(ftp!=null && ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}