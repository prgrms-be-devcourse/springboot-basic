package com.example.demo.view.index;


import com.example.demo.util.IndexMenuCommand;
import org.springframework.stereotype.Component;

@Component
public class IndexView {

    private final InputView inputView;
    private final OutputView outputView;

    public IndexView() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public IndexMenuCommand readCommandOption() {
        outputView.printCommandOptionMessage();
        return inputView.readCommandOption();
    }

}
