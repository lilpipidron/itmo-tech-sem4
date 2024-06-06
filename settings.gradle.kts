include("Bank")
include("Console")
include("api-gateway")
include("cat")
include("owner")
include("domain")
include("cat:cat-core")
findProject(":cat:cat-core")?.name = "cat-core"
include("owner:owner-core")
findProject(":owner:owner-core")?.name = "owner-core"
include("cat:cat-client")
findProject(":cat:cat-client")?.name = "cat-client"
include("owner:owner-client")
findProject(":owner:owner-client")?.name = "owner-client"
