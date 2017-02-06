package com.timejh.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment implements View.OnClickListener {

    // 뒤로가기와 url 로 이동하기 버튼
    Button btnBack, btnGo;
    // Url 입력받는 EditText
    EditText etUrl;
    // WebView
    WebView webView;

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        // 1. 사용할 위젯을 가져온다.
        webView = (WebView) view.findViewById(R.id.webView);
        etUrl = (EditText) view.findViewById(R.id.etUrl);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnGo = (Button) view.findViewById(R.id.btnGo);

        // 2. 리스너 달기
        btnBack.setOnClickListener(this);
        btnGo.setOnClickListener(this);

        // 속도향상 (검토 필요) - 느리다 함... 차이많이 난다 함...
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // script 사용 설정 (필수)
        webView.getSettings().setJavaScriptEnabled(true);
        // 줌사용
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        // 3. 웹뷰 클라이언트를 지정... (안하면 내장 웹브라우저가 팝업된다.)
        webView.setWebViewClient(new WebViewClient());
        // 3.1 둘다 세팅할것 : 프로토콜에 따라 클라이언트가 선택되는것으로 파악됨...
        webView.setWebChromeClient(new WebChromeClient());

        // 최초 로드시 google.com 이동
        webView.loadUrl("https://www.google.com");

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGo:
                // # 입력창에서 url 가져오기
                String url = etUrl.getText().toString();
                goUrl(url);
                break;
            case R.id.btnBack:
                goBack();
                break;
        }
    }

    /**
     * 유효성 검사후 url 로 이동
     * http:// 자동입력
     *
     * @param url
     */
    private void goUrl(String url) {
        // 1. 유효성 검사
        if (url != null && !url.equals("")) {
            // 2. 프로토콜 검사
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            // 3. url 이동
            webView.loadUrl(url);
        }
    }

    /**
     * 뒤로가기
     */
    private void goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            Toast.makeText(getContext(), "뒤로갈 수 없습니다!", Toast.LENGTH_SHORT).show();
        }
    }
}
