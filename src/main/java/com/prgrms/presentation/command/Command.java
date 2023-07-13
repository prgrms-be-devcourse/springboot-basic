package com.prgrms.presentation.command;


import com.prgrms.presentation.view.ViewManager;

public interface Command {
    Power execute(ViewManager viewManager);
}
