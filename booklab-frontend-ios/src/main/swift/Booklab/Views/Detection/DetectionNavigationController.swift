/*
 * Copyright 2018 The BookLab Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Foundation
import UIKit
import Material

public class DetectionNavigationController : UINavigationController {
    public override var prefersStatusBarHidden: Bool {
        return visibleViewController?.prefersStatusBarHidden ?? false
    }
    
    public override func viewDidLoad() {
        super.viewDidLoad()
        
        // Make navigation bar transparent
        navigationBar.setBackgroundImage(UIImage(), for: UIBarMetrics.default)
        navigationBar.shadowImage = UIImage()
        navigationBar.backgroundColor = UIColor.clear
        navigationBar.isTranslucent = true
        
        prepareTabItem()
    }
}

extension DetectionNavigationController {
    fileprivate func prepareTabItem() {
        tabItem.image = Icon.photoCamera
        tabItem.pulseColor = Color.pink.darken1
        tabItem.setTabItemColor(Color.pink.base, for: .selected)
        tabItem.setTabItemColor(Color.pink.accent2, for: .highlighted)
        tabItem.setTabItemColor(Color.blueGrey.base, for: .normal)
    }
}
