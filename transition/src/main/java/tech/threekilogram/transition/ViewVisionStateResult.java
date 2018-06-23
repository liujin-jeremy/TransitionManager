package tech.threekilogram.transition;

/**
 * 用于传递变量
 * @author wuxio 2018-06-23:10:59
 */
class ViewVisionStateResult extends ViewVisionState {

    ViewRelayout viewRelayout;


    public ViewVisionStateResult(ViewRelayout viewRelayout) {

        this.viewRelayout = viewRelayout;
    }
}
