package g.rpc.service;

import g.rpc.server.Service;

@Service(value = MessageService.class)
public class MessageServiceImpl implements MessageService {

	public Message getMessage(String msg) {
		Message message = new Message();
		message.setName("message name");
		message.setContent("message content request-msg:" + msg);
		return message;
	}

}
