
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zbar.lib.animationslib;


import com.zbar.lib.animationslib.attention.BounceAnimator;
import com.zbar.lib.animationslib.attention.FlashAnimator;
import com.zbar.lib.animationslib.attention.PulseAnimator;
import com.zbar.lib.animationslib.attention.RubberBandAnimator;
import com.zbar.lib.animationslib.attention.ShakeAnimator;
import com.zbar.lib.animationslib.attention.StandUpAnimator;
import com.zbar.lib.animationslib.attention.SwingAnimator;
import com.zbar.lib.animationslib.attention.TadaAnimator;
import com.zbar.lib.animationslib.attention.WaveAnimator;
import com.zbar.lib.animationslib.attention.WobbleAnimator;
import com.zbar.lib.animationslib.bouncing_entrances.BounceInAnimator;
import com.zbar.lib.animationslib.bouncing_entrances.BounceInDownAnimator;
import com.zbar.lib.animationslib.bouncing_entrances.BounceInLeftAnimator;
import com.zbar.lib.animationslib.bouncing_entrances.BounceInRightAnimator;
import com.zbar.lib.animationslib.bouncing_entrances.BounceInUpAnimator;
import com.zbar.lib.animationslib.fading_entrances.FadeInAnimator;
import com.zbar.lib.animationslib.fading_entrances.FadeInDownAnimator;
import com.zbar.lib.animationslib.fading_entrances.FadeInLeftAnimator;
import com.zbar.lib.animationslib.fading_entrances.FadeInRightAnimator;
import com.zbar.lib.animationslib.fading_entrances.FadeInUpAnimator;
import com.zbar.lib.animationslib.fading_exits.FadeOutAnimator;
import com.zbar.lib.animationslib.fading_exits.FadeOutDownAnimator;
import com.zbar.lib.animationslib.fading_exits.FadeOutLeftAnimator;
import com.zbar.lib.animationslib.fading_exits.FadeOutRightAnimator;
import com.zbar.lib.animationslib.fading_exits.FadeOutUpAnimator;
import com.zbar.lib.animationslib.flippers.FlipInXAnimator;
import com.zbar.lib.animationslib.flippers.FlipInYAnimator;
import com.zbar.lib.animationslib.flippers.FlipOutXAnimator;
import com.zbar.lib.animationslib.flippers.FlipOutYAnimator;
import com.zbar.lib.animationslib.rotating_entrances.RotateInAnimator;
import com.zbar.lib.animationslib.rotating_entrances.RotateInDownLeftAnimator;
import com.zbar.lib.animationslib.rotating_entrances.RotateInDownRightAnimator;
import com.zbar.lib.animationslib.rotating_entrances.RotateInUpLeftAnimator;
import com.zbar.lib.animationslib.rotating_entrances.RotateInUpRightAnimator;
import com.zbar.lib.animationslib.rotating_exits.RotateOutAnimator;
import com.zbar.lib.animationslib.rotating_exits.RotateOutDownLeftAnimator;
import com.zbar.lib.animationslib.rotating_exits.RotateOutDownRightAnimator;
import com.zbar.lib.animationslib.rotating_exits.RotateOutUpLeftAnimator;
import com.zbar.lib.animationslib.rotating_exits.RotateOutUpRightAnimator;
import com.zbar.lib.animationslib.sliders.SlideInDownAnimator;
import com.zbar.lib.animationslib.sliders.SlideInLeftAnimator;
import com.zbar.lib.animationslib.sliders.SlideInRightAnimator;
import com.zbar.lib.animationslib.sliders.SlideInUpAnimator;
import com.zbar.lib.animationslib.sliders.SlideOutDownAnimator;
import com.zbar.lib.animationslib.sliders.SlideOutLeftAnimator;
import com.zbar.lib.animationslib.sliders.SlideOutRightAnimator;
import com.zbar.lib.animationslib.sliders.SlideOutUpAnimator;
import com.zbar.lib.animationslib.specials.RollInAnimator;
import com.zbar.lib.animationslib.specials.RollOutAnimator;
import com.zbar.lib.animationslib.zooming_entrances.ZoomInAnimator;
import com.zbar.lib.animationslib.zooming_entrances.ZoomInDownAnimator;
import com.zbar.lib.animationslib.zooming_entrances.ZoomInLeftAnimator;
import com.zbar.lib.animationslib.zooming_entrances.ZoomInRightAnimator;
import com.zbar.lib.animationslib.zooming_entrances.ZoomInUpAnimator;
import com.zbar.lib.animationslib.zooming_exits.ZoomOutAnimator;
import com.zbar.lib.animationslib.zooming_exits.ZoomOutDownAnimator;
import com.zbar.lib.animationslib.zooming_exits.ZoomOutLeftAnimator;
import com.zbar.lib.animationslib.zooming_exits.ZoomOutRightAnimator;
import com.zbar.lib.animationslib.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {


    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    RollIn(RollInAnimator.class),
    RollOut(RollOutAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),
    FlipInY(FlipInYAnimator.class),
    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);



    private Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
