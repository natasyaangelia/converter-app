import dependencies.Dependencies
import extensions.implementation

plugins {
    id("commons.android-dynamic-feature")
}

dependencies {
    implementation(project(BuildModules.Commons.VIEWS))

    implementation(Dependencies.RECYCLE_VIEW)
    implementation(Dependencies.SWIPE_REFRESH_LAYOUT)
    implementation(Dependencies.PAGING)
    implementation(Dependencies.MATERIAL_SPINNER)

}
