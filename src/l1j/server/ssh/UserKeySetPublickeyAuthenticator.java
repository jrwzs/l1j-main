package l1j.server.ssh;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.server.auth.pubkey.PublickeyAuthenticator;
import org.apache.sshd.server.session.ServerSession;

public class UserKeySetPublickeyAuthenticator implements PublickeyAuthenticator {
	private static Logger _log = Logger.getLogger(UserKeySetPublickeyAuthenticator.class.getName());
    private final Map<String, List<PublicKey>> userToKeySet;

    public UserKeySetPublickeyAuthenticator(Map<String, List<PublicKey>> userKeysMap) {
        this.userToKeySet = userKeysMap;
    }

    @Override
    public boolean authenticate(String username, PublicKey key, ServerSession session) {
    	boolean keyFound = KeyUtils.findMatchingKey(key, userToKeySet.get(username)) != null;
    	
    	if(keyFound) {
    		_log.log(Level.INFO, username + " successfully authenticated to SSH.");
    	}
    	
        return keyFound;
    }
}