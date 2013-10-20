package com.example.workItem;

import java.util.HashMap;
import java.util.Map;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.jboss.processFlow.knowledgeService.IKnowledgeSession;

import org.apache.log4j.Logger;

public class ChangePInstanceVariable extends BaseWIHandler implements WorkItemHandler {

    private static Logger log = Logger.getLogger("ChangePInstanceVariable");

    public ChangePInstanceVariable() {
        super();
    }

    // only modify pInstance variables (as opposed to completing work item on async branch)
    // thus, parent pInstance will never complete
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        Map<String, Object> taskWICompleterMap = (Map<String,Object>)workItem.getParameter("taskWICompleterMap");
        Long pInstanceId = workItem.getProcessInstanceId();

        log.info("executeWorkItem() now setting process variables on pInstanceId = "+pInstanceId);
        Map<String, Object> pVariables = new HashMap<String, Object>();
        pVariables.put(CLIENT_ID, this.toString());
        kProxy.setProcessInstanceVariables(pInstanceId, pVariables, null);
    
        log.info("executeWorkItem() will now execute completeWorkItem on signal branch...");
        manager.completeWorkItem(workItem.getId(), null);
    }

    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
