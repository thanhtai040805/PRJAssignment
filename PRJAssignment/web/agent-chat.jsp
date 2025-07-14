<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
    if (userId == null || userId <= 0) userId = 0; // fallback nếu chưa đăng nhập
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <title>Agent AI Chat Popup</title>
    <style>
        /* --- Styles for chat button and popup --- */
        #chatBtn {
            position: fixed;
            bottom: 30px;
            right: 30px;
            background: #0084ff;
            color: #fff;
            border: none;
            border-radius: 50%;
            width: 60px;
            height: 60px;
            font-size: 30px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
            cursor: pointer;
            z-index: 1000;
            transition: background 0.2s;
        }
        #chatBtn:hover {
            background: #005bb5;
        }
        #chatPopup {
            display: none;
            position: fixed;
            bottom: 100px;
            right: 30px;
            width: 350px;
            max-width: 90vw;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.25);
            z-index: 1001;
            flex-direction: column;
            overflow: hidden;
            font-family: Arial, sans-serif;
        }
        #chatPopup.flex {
            display: flex;
        }
        #chatHeader {
            background: #0084ff;
            color: #fff;
            padding: 12px 16px;
            font-size: 18px;
            font-weight: bold;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        #closeChat {
            background: none;
            border: none;
            color: #fff;
            font-size: 22px;
            cursor: pointer;
        }
        #chatBody {
            padding: 16px;
            height: 300px;
            overflow-y: auto;
            background: #f5f6fa;
            word-wrap: break-word;
        }
        .chat-msg {
            margin-bottom: 12px;
            line-height: 1.5;
            white-space: pre-wrap;
        }
        .chat-msg.user {
            text-align: right;
            color: #0084ff;
        }
        .chat-msg.agent {
            text-align: left;
            color: #222;
        }
        #chatForm {
            display: flex;
            border-top: 1px solid #eee;
            background: #fff;
        }
        #chatInput {
            flex: 1;
            border: none;
            padding: 12px;
            font-size: 15px;
            outline: none;
        }
        #sendBtn {
            background: #0084ff;
            color: #fff;
            border: none;
            padding: 0 18px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.2s;
        }
        #sendBtn:hover:not(:disabled) {
            background: #005bb5;
        }
        #sendBtn:disabled, #chatInput:disabled {
            opacity: 0.6;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <!-- Nút chat nổi -->
    <button id="chatBtn" title="Chat với Agent AI">&#128172;</button>

    <!-- Popup chat -->
    <div id="chatPopup" role="dialog" aria-modal="true" aria-labelledby="chatHeader" aria-hidden="true">
        <div id="chatHeader">
            Agent AI Chat
            <button id="closeChat" aria-label="Đóng cửa sổ chat">&times;</button>
        </div>
        <div id="chatBody" aria-live="polite" aria-relevant="additions"></div>
        <form id="chatForm" autocomplete="off" aria-label="Form gửi tin nhắn chat">
            <input type="text" id="chatInput" placeholder="Nhập câu hỏi..." required aria-required="true" aria-describedby="inputHelp" />
            <button type="submit" id="sendBtn">Gửi</button>
        </form>
    </div>

    <script>
        const userId = <%= userId %>;

        const chatBtn = document.getElementById('chatBtn');
        const chatPopup = document.getElementById('chatPopup');
        const closeChat = document.getElementById('closeChat');
        const chatBody = document.getElementById('chatBody');
        const chatForm = document.getElementById('chatForm');
        const chatInput = document.getElementById('chatInput');
        const sendBtn = document.getElementById('sendBtn');

        // Hiển thị popup chat
        chatBtn.addEventListener('click', () => {
            chatPopup.classList.add('flex');
            chatPopup.setAttribute('aria-hidden', 'false');
            chatInput.focus();
        });

        // Đóng popup chat
        closeChat.addEventListener('click', () => {
            chatPopup.classList.remove('flex');
            chatPopup.setAttribute('aria-hidden', 'true');
        });

        // Kiểm tra đăng nhập
        if (!userId || userId <= 0) {
            chatInput.disabled = true;
            sendBtn.disabled = true;
            appendMsg("Vui lòng đăng nhập để sử dụng Agent AI.", "agent");
        }

        // Hàm thêm tin nhắn vào chat
        function appendMsg(msg, sender) {
            const div = document.createElement('div');
            div.className = 'chat-msg ' + sender;
            div.textContent = msg;
            chatBody.appendChild(div);
            chatBody.scrollTop = chatBody.scrollHeight;

            // Giới hạn số tin nhắn trong chat (giữ 50 tin nhắn)
            while (chatBody.childNodes.length > 50) {
                chatBody.removeChild(chatBody.firstChild);
            }
        }

        // Gửi tin nhắn
        chatForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const prompt = chatInput.value.trim();
            if (!prompt || !userId || userId <= 0) return;

            appendMsg(prompt, 'user');
            chatInput.value = '';
            chatInput.focus();
            sendBtn.disabled = true;

            const loadingMsg = "Đang trả lời...";
            appendMsg(loadingMsg, 'agent');

            try {
                const response = await fetch('<%=request.getContextPath()%>/agent', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'userId=' + encodeURIComponent(userId) + '&prompt=' + encodeURIComponent(prompt)
                });

                if (!response.ok) throw new Error('Lỗi kết nối');

                const answer = await response.text();

                // Xóa tin nhắn "Đang trả lời..."
                const msgs = chatBody.querySelectorAll('.chat-msg.agent');
                if (msgs.length) {
                    const lastAgentMsg = msgs[msgs.length - 1];
                    if (lastAgentMsg.textContent === loadingMsg) {
                        lastAgentMsg.remove();
                    }
                }

                appendMsg(answer, 'agent');
            } catch (error) {
                // Xóa tin nhắn "Đang trả lời..."
                const msgs = chatBody.querySelectorAll('.chat-msg.agent');
                if (msgs.length) {
                    const lastAgentMsg = msgs[msgs.length - 1];
                    if (lastAgentMsg.textContent === loadingMsg) {
                        lastAgentMsg.remove();
                    }
                }
                appendMsg("Có lỗi khi kết nối Agent AI.", "agent");
            } finally {
                sendBtn.disabled = false;
            }
        });

        // Hỗ trợ gửi bằng phím Enter
        chatInput.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                if (!sendBtn.disabled) chatForm.dispatchEvent(new Event('submit'));
            }
        });
    </script>
</body>
</html>
