//
//  WildlifeMarkerView.swift
//  CampusSafari
//
//  Created by Devon Long on 11/17/19.
//  Copyright © 2019 Team 9323. All rights reserved.
//

import MapKit

class WildlifeMarkerView: MKMarkerAnnotationView {
    override var annotation: MKAnnotation? {
        willSet {
            guard let wildlife = newValue as? Wildlife else { return }
            canShowCallout = true
            calloutOffset = CGPoint(x: -5, y: 5)
            rightCalloutAccessoryView = UIButton(type: .detailDisclosure)
            markerTintColor = wildlife.markerTintColor
            glyphText = String(wildlife.kingdom.first!)
        }
    }
}
