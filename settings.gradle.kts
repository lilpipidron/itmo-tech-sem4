include("Bank")
include("Console")
include("dao")
include("service")
include("controller")
include("api-gateway")
include("cat")
include("owner")
include("cat-client")
include("owner-client")
include("domain")
include("cat:cat-core")
findProject(":cat:cat-core")?.name = "cat-core"
include("owner:owner-core")
findProject(":owner:owner-core")?.name = "owner-core"
include("cat:client")
findProject(":cat:client")?.name = "client"
include("owner:client")
findProject(":owner:client")?.name = "client"
